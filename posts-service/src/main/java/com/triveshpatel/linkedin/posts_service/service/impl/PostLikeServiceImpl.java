package com.triveshpatel.linkedin.posts_service.service.impl;

import com.triveshpatel.linkedin.posts_service.entity.PostLike;
import com.triveshpatel.linkedin.posts_service.exception.BadRequestException;
import com.triveshpatel.linkedin.posts_service.exception.ResourceNotFoundException;
import com.triveshpatel.linkedin.posts_service.repository.PostLikeRepository;
import com.triveshpatel.linkedin.posts_service.repository.PostsRepository;
import com.triveshpatel.linkedin.posts_service.service.PostLikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;


    @Override
    public void likePost(Long postId, Long userId) {
        log.info("Attempting to like the post with Id: {}",postId);
        boolean exists = postsRepository.existsById(postId);
        if(!exists)throw new ResourceNotFoundException("Post not found with Id:"+ postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(alreadyLiked)throw new BadRequestException("Cannot like the same post again.");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Post with Id: {} liked successfully", postId);
    }

    @Override
    @Transactional
    public void unlikePost(Long postId, Long userId) {
        log.info("Attempting to unlike the post with Id: {}", postId);
        boolean exists = postLikeRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with Id:"+ postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!alreadyLiked) throw new BadRequestException("Cannot unlike the post which is not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        log.info("Post with Id: {} unliked successfully", postId);
    }
}
