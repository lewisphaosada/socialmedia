package com.cooksys.TwitterMockupAPI.dtos;


import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetResponseDto {

    private Long id;

    private String content;

    private UserResponseDto author;

    private Timestamp posted;

    private boolean deleted = false;

    private TweetResponseDto inReplyTo;

    private TweetResponseDto repostOf;
}
