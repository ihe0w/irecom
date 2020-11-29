package com.example.business_server.service.impl;

import com.example.business_server.model.recom.Recommendation;
import com.example.business_server.service.RecommendService;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

    }


//    public boolean put(String string, int numHashFunctions, RedisBitmaps bits) {
//        long bitSize = bits.bitSize();
//        byte[] bytes = Hashing.murmur3_128().hashString(string, Charsets.UTF_8).asBytes();
//        long hash1 = lowerEight(bytes);
//        long hash2 = upperEight(bytes);
//
//        boolean bitsChanged = false;
//        long combinedHash = hash1;
////        for (int i = 0; i < numHashFunctions; i++) {
////            bitsChanged |= bits.set((combinedHash & Long.MAX_VALUE) % bitSize);
////            combinedHash += hash2;
////        }
//        long[] offsets = new long[numHashFunctions];
//        for (int i = 0; i < numHashFunctions; i++) {
//            offsets[i] = (combinedHash & Long.MAX_VALUE) % bitSize;
//            combinedHash += hash2;
//        }
//        bitsChanged = bits.set(offsets);
//        bits.ensureCapacityInternal();//自动扩容
//        return bitsChanged;
//    }
//
//    @Override
//    public boolean mightContain(String object, int numHashFunctions, RedisBitmaps bits) {
//        long bitSize = bits.bitSize();
//        byte[] bytes = Hashing.murmur3_128().hashString(object, Charsets.UTF_8).asBytes();
//        long hash1 = lowerEight(bytes);
//        long hash2 = upperEight(bytes);
//        long combinedHash = hash1;
////        for (int i = 0; i < numHashFunctions; i++) {
////            if (!bits.get((combinedHash & Long.MAX_VALUE) % bitSize)) {
////                return false;
////            }
////            combinedHash += hash2;
////        }
////        return true;
//        long[] offsets = new long[numHashFunctions];
//        for (int i = 0; i < numHashFunctions; i++) {
//            offsets[i] = (combinedHash & Long.MAX_VALUE) % bitSize;
//            combinedHash += hash2;
//        }
//        return bits.get(offsets);
//    }
}
