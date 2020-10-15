package com.example.business_server;

import com.example.business_server.model.recom.Recommendation;
import com.example.business_server.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ServiceTest extends BusinessServerApplicationTests{
    @Autowired
    RecommendService recommendService;

    @Test
    public void testMDb(){
        log.info("is there log?");
        List<Recommendation> results=recommendService.getCollaborativeFilteringRecommendations((long) 123,2);
        assertThat(results,nullValue());
    }
}
