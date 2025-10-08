package com.triveshpatel.linkedIn.posts_service.service;

import com.triveshpatel.linkedIn.posts_service.dto.PostCreateRequestDto;
import com.triveshpatel.linkedIn.posts_service.dto.PostDto;
import java.util.List;

public interface PostsService {


    PostDto createPost(PostCreateRequestDto postCreateRequestDto);

    PostDto getPostById(Long postId);

    List<PostDto> getAllPostsOfUser(Long userId);
}
