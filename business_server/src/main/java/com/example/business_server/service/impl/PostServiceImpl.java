package com.example.business_server.service.impl;

import com.example.business_server.dao.PostMapper;
import com.example.business_server.dao.UserMapper;
import com.example.business_server.model.domain.Comment;
import com.example.business_server.model.domain.Post;
import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.*;
import com.example.business_server.model.nosql.Recommendation;
import com.example.business_server.producer.RabbitProducer;
import com.example.business_server.service.PostService;
import com.example.business_server.utils.FileUtil;
import com.example.business_server.utils.SortUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PostServiceImpl implements PostService {
    @Value("${constant.resPath}")
    private String resPath;

    private final UserMapper userMapper;
    private final RabbitProducer rabbitProducer;
    private final RedisTemplate<String,String> redisTemplate;
    private final PostMapper postMapper;

    public PostServiceImpl(PostMapper postMapper, RedisTemplate<String, String> redisTemplate, UserMapper userMapper, RabbitProducer rabbitProducer) {
        this.postMapper = postMapper;
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.rabbitProducer = rabbitProducer;
    }


    @Override
    public List<Post> findPostsByRecommendations(List<Recommendation> recommendations) {
        List<Post> posts=new ArrayList<>();

        for (Recommendation recommendation :
                recommendations) {
            posts.add(getPostById(recommendation.getItemId()));
        }

        return posts;
    }

    /**
     * 更新like*/
    @Override
    public ResponseResult updateLike(LikeDTO likeDTO) {
        //
        if (likeDTO.getStatus()){
            postMapper.insertLike(likeDTO.getUserId(),likeDTO.getPostId());
        }
        else {
            postMapper.deleteLike(likeDTO.getUserId(),likeDTO.getPostId());
        }
        return ResponseResult.success();
    }


    /**
     * 添加评论*/
    @Override
    public ResponseResult addComment(CommentDTO commentDTO){
        Comment comment=new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPostId(commentDTO.getPostId());
        comment.setReplyCommentId(commentDTO.getReplyCommentId());
        comment.setCommentUserId(commentDTO.getCommentUserId());

        postMapper.insertComment(comment);
        return ResponseResult.success();
    }


    /**
     * 删除评论*/
    @Override
    public ResponseResult deleteComment(Long commentId){
        postMapper.deleteComment(commentId);
        return ResponseResult.success();
    }

    /**
     * 上传post*/
    @Override
    public ResponseResult uploadPost(PostDTO postDTO) {
        // 存储post多媒体文件
        List<MultipartFile> files=postDTO.getFiles();
        Post post=postDTO.getPost();
        List<String> urls;
        String url;
        for (MultipartFile file :
                files) {
            try {
                // 将文件存储到mysql
                // todo: 这里需要有一个事务，防止postupload到一半突然发生意外
                url= FileUtil.uploadFile(file,resPath);
                // url,post映射存储到redis
                redisTemplate.opsForList().leftPush(String.valueOf(post.getPostId()),url);

            }
            catch (IOException e) {
                return ResponseResult.failed(ResultCode.UPLOAD_FAIL);
            }
        }

        postMapper.insertPost(post);
        return ResponseResult.success();
    }

    /**
     * 刪除post*/
    @Override
    public ResponseResult deletePost(Long postId){
        // 删除多媒体文件
        List<String> urls=redisTemplate.opsForList().range(String.valueOf(postId),0,-1);
        for (String url:
             urls) {
            FileUtil.deleteFile(url);
        }

        // todo 删除操作需要事务
        // 刪除redis中url
        redisTemplate.delete(String.valueOf(postId));

        // 删除post
        postMapper.deletePostById(postId);

        return ResponseResult.success();

    }

    /**
     * 根据user按照上传时间获得排序后的post*/
    @Override
    public ResponseResult<List<Post>> getPostsByUser(Long userId){
        List<Post> posts=postMapper.selectPostsByUserOrderByCreateTime(userId);
        for (Post post:
             posts) {
            post.setResUrls(redisTemplate.opsForList().range(String.valueOf(post.getPostId()),0,-1));
        }
        return ResponseResult.success(posts);
    }

    /**
     * 获得关注用户按发表顺序排序的post*/
    @Override
    public ResponseResult<List<Post>> followerPosts(Long followerId) {
        // 查询到所有关注者
        List<User> posters=userMapper.selectPosterIdByFollow(followerId);

        // 根据所有关注者获得所有post
        List<List<Post>> posterPosts=new ArrayList<>(posters.size());
        for (User poster :
                posters) {
            posterPosts.add(postMapper.selectPostsByUserOrderByCreateTime(poster.getUserId()));
        }

        // 根据post发表时间进行排序
        return ResponseResult.success(SortUtil.mergeMultipleSortedArray(posterPosts));
    }


    /**
     * 根据id获取post
     * */
    @Cacheable(value = "recommendationCache" , key = "#id")
    public Post getPostById(long id){
        CompletableFuture<Post> getPost=this.findPostById(id);
        CompletableFuture<List<String>> getUrls=this.findImgUrlsByItemId(id);
        CompletableFuture.allOf(getPost,getUrls).join();
        Post post= null;
        try {
            post = getPost.get();
            post.setResUrls(getUrls.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Async
    CompletableFuture<List<String>> findImgUrlsByItemId(Long itemId){
        List<String> urls=redisTemplate.opsForList().range(String.valueOf(itemId),0,-1);
        return CompletableFuture.completedFuture(urls);
    }

    @Async
    CompletableFuture<Post> findPostById(Long itemId){
        Post post=postMapper.findPostById(itemId);
        return CompletableFuture.completedFuture(post);
    }
}
