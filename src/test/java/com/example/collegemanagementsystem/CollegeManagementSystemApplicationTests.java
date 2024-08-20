package com.example.collegemanagementsystem;

import com.example.collegemanagementsystem.clients.PostClient;
import com.example.collegemanagementsystem.dtos.PostDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CollegeManagementSystemApplicationTests {

    @Autowired
    private PostClient postClient;

//    @Test
//    @Order(3)
//    void getAllPostTest() {
//        List<PostDto> employeeDTOList = postClient.getAllPosts();
//        System.out.println(employeeDTOList);
//    }
//
//    @Test
//    @Order(2)
//    void getPostByIdTest() {
//        PostDto post = postClient.getPostById(1L);
//        System.out.println(post);
//    }
//
//    @Test
//    @Order(1)
//    void createNewPostTest() {
//        PostDto postDto = new PostDto(null,"Waterfall Picture","Waterfall Picture");
//        PostDto savedPost = postClient.createNewPost(postDto);
//        System.out.println(savedPost);
//    }

}
