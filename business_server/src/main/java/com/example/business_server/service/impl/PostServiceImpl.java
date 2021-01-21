package com.example.business_server.service.impl;

import com.example.business_server.dao.PostMapper;
import com.example.business_server.model.domain.Post;
import com.example.business_server.model.recom.Recommendation;
import com.example.business_server.producer.RabbitProducer;
import com.example.business_server.service.PostService;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private RabbitProducer rabbitProducer;
    private final RedisTemplate<String,String> redisTemplate;
    private final PostMapper postMapper;

    public PostServiceImpl(PostMapper postMapper, RedisTemplate<String,String> redisTemplate) {
        this.postMapper = postMapper;
        this.redisTemplate = redisTemplate;
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
            post.setImgUrls(getUrls.get());
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
