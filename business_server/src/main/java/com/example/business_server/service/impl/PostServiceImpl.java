package com.example.business_server.service.impl;

import com.example.business_server.dao.PostMapper;
import com.example.business_server.model.domain.Post;
import com.example.business_server.model.recom.Recommendation;
import com.example.business_server.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;

    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<Post> findPostsByRecommendations(List<Recommendation> recommendations) {
        List<Post> posts=new ArrayList<>();

        for (Recommendation recommendation :
                recommendations) {
            posts.add(postMapper.findPostById(recommendation.getItemId()));
        }

        return posts;
    }
}
