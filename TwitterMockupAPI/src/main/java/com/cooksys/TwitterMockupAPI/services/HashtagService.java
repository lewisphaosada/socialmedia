package com.cooksys.TwitterMockupAPI.services;

import com.cooksys.TwitterMockupAPI.dtos.HashtagDto;
import com.cooksys.TwitterMockupAPI.dtos.TweetResponseDto;
import com.cooksys.TwitterMockupAPI.entities.Hashtag;
import com.cooksys.TwitterMockupAPI.entities.Tweet;

import java.util.List;


public interface HashtagService {

    List<HashtagDto> getAllTags();

    List<TweetResponseDto> getTweetsByHashTag(String label);
}