package com.cow.cow_mvc_practice.post.service;

import com.cow.cow_mvc_practice.post.controller.dto.request.CreatePostRequest;
import com.cow.cow_mvc_practice.post.controller.dto.response.CreatePostResponse;

public interface PostService {

   CreatePostResponse createPost(CreatePostRequest createPostRequest);
}