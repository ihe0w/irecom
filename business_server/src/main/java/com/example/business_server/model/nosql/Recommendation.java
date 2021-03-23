package com.example.business_server.model.nosql;

import com.example.business_server.common.DBConstant;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 推荐项目的包装
 */
@Data
@Document(collection = DBConstant.MONGODB_USER_RECS_COLLECTION)
public class Recommendation {
    @Field("item_id")
    private Long itemId;
    private Double score;

}
