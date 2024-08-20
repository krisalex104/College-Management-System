package com.example.collegemanagementsystem.clients.impl;

import com.example.collegemanagementsystem.advices.ApiResponse;
import com.example.collegemanagementsystem.clients.PostClient;
import com.example.collegemanagementsystem.configs.RestClientConfig;
import com.example.collegemanagementsystem.dtos.PostDto;
import com.example.collegemanagementsystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostClientImpl implements PostClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(PostClientImpl.class);

    @Override
    public List<PostDto> getAllPosts() {
        log.trace("Trying to retrieve all posts in getAllPosts");
        try {
            log.info("Attempting to call the restClient Method in getAllPosts");
            ApiResponse<List<PostDto>> postDtoList = restClient.get()
                    .uri("posts")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the posts");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            log.debug("Successfully retrieved the posts in getAllPosts");
            log.trace("Retrieved post list in getAllPosts : {}, {}, {}", postDtoList.getData(), "Hello", 5);
            return postDtoList.getData();
        } catch (Exception e) {
            log.error("Exception occurred in getAllPosts", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public PostDto getPostById(Long postId) {
        log.trace("Trying to get post By Id in getPostById with id: {}", postId);
        try {
            ApiResponse<PostDto> postResponse = restClient.get()
                    .uri("posts/{postId}", postId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the post");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            return postResponse.getData();
        } catch (Exception e) {
            log.error("Exception occurred in getPost", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public PostDto createNewPost(PostDto postDto) {
        log.trace("Trying to create Post with information {}", postDto);
        try {
            ResponseEntity<ApiResponse<PostDto>> postDTOApiResponse = restClient.post()
                    .uri("posts")
                    .body(postDto)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.debug("4xxClient error occurred during createNewPost");
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the post");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
            log.trace("Successfully created a new post : {}", postDTOApiResponse.getBody());
            return postDTOApiResponse.getBody().getData();
        }
        catch (Exception e) {
            log.error("Exception occurred in createNewPost", e);
            throw new RuntimeException(e);
        }
    }
}
