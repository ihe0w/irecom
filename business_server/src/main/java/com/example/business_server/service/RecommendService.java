package com.example.business_server.service;

import com.example.business_server.model.recom.Recommendation;

import java.util.List;

/**
 * @author ihewe
 */
public interface RecommendService {
    /**
     * @param userId
     * @param number
     * @return
     */
    List<Recommendation> getCollaborativeFilteringRecommendations(Long userId, Integer number);
    List<Recommendation> getRealTimeRecommendations(Long userId, Long postId);
}
