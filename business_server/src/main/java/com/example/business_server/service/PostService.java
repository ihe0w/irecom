package com.example.business_server.service;

import com.example.business_server.model.domain.Post;
import com.example.business_server.model.dto.CommentDTO;
import com.example.business_server.model.dto.LikeDTO;
import com.example.business_server.model.dto.PostDTO;
import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.model.nosql.Recommendation;

import java.util.List;

public interface PostService {
    List<Post> findPostsByRecommendations(List<Recommendation> recommendations);

    ResponseResult updateLike(LikeDTO likeDTO);

    ResponseResult addComment(CommentDTO commentDTO);

    ResponseResult deleteComment(Long commentId);

    ResponseResult uploadPost(PostDTO postDTO);

    ResponseResult deletePost(Long postId);

    ResponseResult<List<Post>> getPostsByUser(Long userId);

    ResponseResult<List<Post>> followerPosts(Long userId);
}
