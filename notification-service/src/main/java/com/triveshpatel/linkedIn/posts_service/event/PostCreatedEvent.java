package com.triveshpatel.linkedIn.posts_service.event;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreatedEvent {

    Long creatorId;
    String content;
    Long postId;
}
