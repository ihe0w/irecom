package com.example.business_server.service.impl;

import com.example.business_server.dao.PostMapper;
import com.example.business_server.model.domain.Post;
import com.example.business_server.model.recom.Recommendation;
import com.example.business_server.service.PostService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
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
            Post post=postMapper.findPostById(recommendation.getItemId());

            List<String> urls=findImgUrlsByItemId(recommendation.getItemId());
            post.setImgUrls(urls);

            posts.add(post);
        }

        return posts;
    }

    List<String> findImgUrlsByItemId(Long itemId){
        List<String> urls=redisTemplate.opsForList().range(String.valueOf(itemId),0,-1);
        return urls;
    }
}
