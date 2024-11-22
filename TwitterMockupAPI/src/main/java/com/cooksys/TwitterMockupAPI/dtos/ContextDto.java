package com.cooksys.TwitterMockupAPI.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {
    //main tweet
    private TweetResponseDto target;

    //tweets prior to the main(target) tweet
    private List<TweetResponseDto> before;

    //tweets after the main(target) tweet
    private List<TweetResponseDto> after;
}
