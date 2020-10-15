package com.example.business_server.service;

import com.example.business_server.model.domain.Post;
import com.example.business_server.model.recom.Recommendation;

import java.util.List;

public interface PostService {
    List<Post> findPostsByRecommendations(List<Recommendation> recommendations);
}
