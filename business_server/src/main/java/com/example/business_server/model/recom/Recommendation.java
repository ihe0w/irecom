package com.example.business_server.model.recom;

import com.example.business_server.utils.Constant;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 推荐项目的包装
 */
@Data
@Document(collection = Constant.MONGODB_USER_RECS_COLLECTION)
public class Recommendation {
    @Field("item_id")
    private Long itemId;
    private Double score;

}
