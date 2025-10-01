package com.triveshpatel.linkedin.posts_service.service;

import com.triveshpatel.linkedin.posts_service.dto.PostCreateRequestDto;
import com.triveshpatel.linkedin.posts_service.dto.PostDto;
import java.util.List;

public interface PostsService {


    PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId);

    PostDto getPostById(Long postId);

    List<PostDto> getAllPostsOfUser(Long userId);
}
