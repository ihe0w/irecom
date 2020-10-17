package com.example.business_server;

import com.example.business_server.dao.PostMapper;
import com.example.business_server.model.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@Slf4j
public class DaoTest extends BusinessServerApplicationTests{
    @Autowired
    PostMapper postMapper;
    @Autowired
    RedisTemplate<String, String> redisTemplate;


    @Test
    public void postDaoTest(){
        Post post=postMapper.findPostById((long) 123);

        assertThat(post,notNullValue(Post.class));
//        log.debug("post "+post.getImgUrls());

    }
    @Test
    public void testPostDel(){
        Integer num=postMapper.deletePostById((long)123);
        log.debug("I think it should be saved in file");
        assertThat(num,is(1));
    }
//    @Test
//    public void testPostIns(){
//        Post post=new Post();
//        post.setPostId((long)908);
//        post.setImgUrls("www.google.com");
//        Integer result=postMapper.insertPost(post);
//        assertThat(result,is((long)908));
//    }

    @Test
    public void testRedisSel(){
        String postId="456";
//        String url1="www.baidu.com";
//        String url2="www.google.com";
//        List<String> urls=new ArrayList<>();
//        urls.add(url1);
//        urls.add(url2);
//        redisTemplate.opsForList().rightPush(postId,urls);
        List<String> urls =redisTemplate.opsForList().range(postId,0,-1);
        log.info("pop urls");
        log.info(urls.toString());
    }

    @Test
    public void testRedisIns(){
        List<String> postIds= Arrays.asList("235", "456", "908","123");
//        List<String> postIds= Arrays.asList("123");
        List<String> imgUrls=Arrays.asList("https://images.pexels.com/photos/2584055/pexels-photo-2584055.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/998904/pexels-photo-998904.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/2793453/pexels-photo-2793453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/4099385/pexels-photo-4099385.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/3755553/pexels-photo-3755553.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        for(String postId:
                postIds){
            Integer randomCount=(int)(1+Math.random()*3);
            log.info("random count ");
            log.info(String.valueOf(randomCount));
            for (int i = 0; i < randomCount; i++) {
                redisTemplate.opsForList().rightPush(postId,imgUrls.get((int)(Math.random()*imgUrls.size())));
            }


        }

    }

    @Test
    public void testRedisDel(){
        List<String> postIds= Arrays.asList("235", "456", "908","123");
        for (String postId :
                postIds) {
            redisTemplate.opsForList().leftPop(postId);
        }
    }
}
