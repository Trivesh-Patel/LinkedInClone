package com.triveshpatel.linkedIn.posts_service.event;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLikedEvent {

    Long postId;
    Long creatorId;
    Long likedByUserId;
}
