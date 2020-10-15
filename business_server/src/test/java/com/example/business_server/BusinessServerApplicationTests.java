package com.example.business_server;

import com.example.business_server.dao.PostMapper;
import com.example.business_server.model.domain.Post;
import com.example.business_server.model.recom.Recommendation;
import com.example.business_server.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
@SpringBootTest
class BusinessServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
