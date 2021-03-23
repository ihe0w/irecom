package com.example.business_server.controller;

import com.example.business_server.model.domain.Post;
import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.CommentDTO;
import com.example.business_server.model.dto.LikeDTO;
import com.example.business_server.model.dto.PostDTO;
import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.service.PostService;
import com.example.business_server.service.RecommendService;
import com.example.business_server.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("api/post")
@CrossOrigin
public class PostController {
    final
    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 喜欢post更新*/
    @PostMapping("/updateLike")
    @ResponseBody
    @ApiOperation(value = "更新post喜欢")
    public ResponseResult updateLike(@RequestBody LikeDTO likeDTO){
        return postService.updateLike(likeDTO);
    }

    /**
     * 新增评论*/
    @PostMapping("/addComment")
    @ResponseBody
    @ApiOperation(value = "新增评论")
    public ResponseResult addComment(@RequestBody CommentDTO commentDTO){
        return postService.addComment(commentDTO);
    }

    /**
     * 喜欢post更新*/
    @GetMapping("/deleteComment")
    @ResponseBody
    @ApiOperation(value = "删除评论")
    public ResponseResult deleteComment(@RequestParam Long commentId){
        return postService.deleteComment(commentId);
    }

    /**
     * 上传post*/
    @PostMapping("/uploadPost")
    @ResponseBody
    @ApiOperation(value = "上传post")
    public ResponseResult uploadPost(@RequestBody PostDTO postDTO){
        return postService.uploadPost(postDTO);
    }

    /**
     * 删除post*/
    @GetMapping("/deletePost")
    @ResponseBody
    @ApiOperation(value = "删除post")
    public ResponseResult deletePost(@RequestParam Long postId){
        return postService.deletePost(postId);
    }

    /**
     * 查询所有关注用户的post，并按照发表时间排序*/
    @GetMapping("/followerPosts")
    @ResponseBody
    @ApiOperation(value = "查询所有关注用户的post，并按照发表时间排序")
    public ResponseResult<List<Post>> followerPosts(@RequestParam Long userId){
        return postService.followerPosts(userId);
    }


}
