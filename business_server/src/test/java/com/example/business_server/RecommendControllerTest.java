package com.example.business_server;

import com.example.business_server.controller.BaseController;
import com.example.business_server.controller.RecommendController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(BaseController.class)
public class RecommendControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testOfflineRecommend() throws Exception {
//        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/api/recommend/explore")
//        .param("userId","1").param("num","1")).andReturn();
        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/api/hello")).andReturn();

        MockHttpServletResponse response=result.getResponse();
        String content=response.getContentAsString();
        log.debug(content);
    }

}
