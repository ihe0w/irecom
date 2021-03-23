package com.example.business_server.model.dto;

import com.example.business_server.model.domain.Post;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostDTO {
    // post图片视频等
    List<MultipartFile> files;

    Post post;
}
