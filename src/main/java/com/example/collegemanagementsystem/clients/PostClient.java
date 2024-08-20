package com.example.collegemanagementsystem.clients;

import com.example.collegemanagementsystem.dtos.PostDto;

import java.util.List;

public interface PostClient {

    List<PostDto> getAllPosts();

    PostDto getPostById(Long postId);

    PostDto createNewPost(PostDto postDto);

}
