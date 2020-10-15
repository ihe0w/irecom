package com.example.business_server.controller;

import com.example.business_server.model.domain.Post;
import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.model.recom.Recommendation;
import com.example.business_server.service.PostService;
import com.example.business_server.service.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.business_server.model.dto.ResponseResult.success;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("api/recommend")
@Api(value = "推荐" ,description = "与推荐相关的Api")
public class RecommendController {
    private final PostService postService;
    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService, PostService postService) {
        this.recommendService = recommendService;
        this.postService = postService;
    }

    @ApiOperation(
            value = "线下推荐",
            notes = "",
            produces = "application/json",
            consumes = "application/json",
            response = List.class
    )
    @GetMapping("/explore")
    @ResponseBody
    public ResponseResult<List<Post>> recommendPosts(@RequestParam("userId")Long userId, @RequestParam("num")Integer num){
        log.debug("enter controller");
        List<Recommendation> recommendations=recommendService.getCollaborativeFilteringRecommendations(userId, num);

        if(recommendations.size()==0){
            // using other recommend algorithm
            log.debug("somthing wrong");
        }

        List<Post> posts=postService.findPostsByRecommendations(recommendations);

        return ResponseResult.success(posts);
    }
}
