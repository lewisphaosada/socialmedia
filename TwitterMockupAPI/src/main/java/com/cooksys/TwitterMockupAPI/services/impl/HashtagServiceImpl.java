package com.cooksys.TwitterMockupAPI.services.impl;

import com.cooksys.TwitterMockupAPI.dtos.TweetResponseDto;
import com.cooksys.TwitterMockupAPI.entities.Tweet;
import com.cooksys.TwitterMockupAPI.exceptions.NotFoundException;
import com.cooksys.TwitterMockupAPI.mappers.HashTagMapper;
import com.cooksys.TwitterMockupAPI.mappers.TweetMapper;
import com.cooksys.TwitterMockupAPI.repositories.HashtagRepository;
import com.cooksys.TwitterMockupAPI.repositories.TweetRepository;
import org.springframework.stereotype.Service;

import com.cooksys.TwitterMockupAPI.dtos.HashtagDto;
import com.cooksys.TwitterMockupAPI.entities.Hashtag;
import com.cooksys.TwitterMockupAPI.services.HashtagService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashTagMapper hashTagMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<HashtagDto> getAllTags() {

        return hashTagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    @Override
    public List<TweetResponseDto> getTweetsByHashTag(String label){

        Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel(label);

        if(optionalHashtag.isEmpty()){
            throw new NotFoundException("Hashtag does not exist");
        }
        Hashtag hashtag = optionalHashtag.get();

        List<Tweet> tweets = tweetRepository.findByHashtagsContainingAndDeletedFalseOrderByPostedDesc(hashtag);

        if(tweets.isEmpty()){
            throw new NotFoundException("No tweets found");
        }

        return tweetMapper.entitiesToResponseDtos(tweets);
    }

    }
