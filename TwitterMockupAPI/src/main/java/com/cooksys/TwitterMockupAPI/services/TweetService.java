package com.cooksys.TwitterMockupAPI.services;

import com.cooksys.TwitterMockupAPI.dtos.*;
import com.cooksys.TwitterMockupAPI.entities.Tweet;

import java.util.List;

public interface TweetService {


    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweetById(Long id);

    List<UserResponseDto> mentionedUsers(Long id);

    List<TweetResponseDto> getTweetReplies(Long id);

    TweetResponseDto repostTweet(Long id, TweetRequestDto tweetRequestDto);

    void postLike(Long id, CredentialsDto credentialsDto);

    List<HashtagDto> getTagById(Long id);

    List<TweetResponseDto> getReposts(Long id);

    List<UserResponseDto> getLikes(Long id);

    ContextDto getContext(Long id);

    TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

    TweetResponseDto postTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto postReply(Long id, TweetRequestDto tweetRequestDto);

}
