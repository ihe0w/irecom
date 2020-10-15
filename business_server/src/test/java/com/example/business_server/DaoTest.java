package com.example.business_server;

import com.example.business_server.dao.PostMapper;
import com.example.business_server.model.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@Slf4j
public class DaoTest extends BusinessServerApplicationTests{
    @Autowired
    PostMapper postMapper;

    @Test
    public void postDaoTest(){
        Post post=postMapper.findPostById((long) 123);

        assertThat(post,notNullValue(Post.class));
        log.debug("post "+post.getImgUrl());

    }
    @Test
    public void testPostDel(){
        Integer num=postMapper.deletePostById((long)123);
        log.debug("I think it should be saved in file");
        assertThat(num,is(1));
    }
    @Test
    public void testPostIns(){
        Post post=new Post();
        post.setPostId((long)908);
        post.setImgUrl("www.google.com");
        Integer result=postMapper.insertPost(post);
        assertThat(result,is((long)908));
    }
}
