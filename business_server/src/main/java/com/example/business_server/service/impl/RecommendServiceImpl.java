package com.example.business_server.service.impl;

import com.example.business_server.model.recom.Recommendation;
import com.example.business_server.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {

    private final MongoTemplate mongoTemplate;

    public RecommendServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Recommendation> getCollaborativeFilteringRecommendations(Long userId, Integer number) {
        log.debug("enter service");
        return findUserCFRecs(userId, number);
    }

    @Override
    public List<Recommendation> getRealTimeRecommendations(Long userId, Long postId) {
        return null;
    }


    // 协同过滤推荐【用户电影矩阵】
    private List<Recommendation> findUserCFRecs(Long userId, Integer maxItems) {

        Query query=Query.query(Criteria.where("user_id").is(userId));
        query.with(Sort.by(new Sort.Order(Sort.Direction.ASC,"_id")));
        List<Recommendation> recommendations=mongoTemplate.find(query,Recommendation.class);

        log.debug("enter here");
        for (Recommendation recom :
                recommendations) {
            log.info("recomm");
            log.info(recom.getScore().toString());
        }
        return recommendations;

//        DBCollection<Document> postRecsCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_USER_RECS_COLLECTION);
//        Document userRecs = postRecsCollection.find(new Document("user_id", userId)).first();
//        return parseRecs(userRecs, maxItems);
    }
}
