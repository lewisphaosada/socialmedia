package com.cooksys.TwitterMockupAPI.controllers;


import com.cooksys.TwitterMockupAPI.dtos.TweetResponseDto;
import com.cooksys.TwitterMockupAPI.entities.Tweet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.cooksys.TwitterMockupAPI.dtos.HashtagDto;
import com.cooksys.TwitterMockupAPI.services.HashtagService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagDto> getAllTags(){
        return hashtagService.getAllTags();
    }

    @GetMapping("/{label}")
    public List<TweetResponseDto> getTweetsByHashTag(@PathVariable String label){
        String formatLabel = "#" + label;
        return hashtagService.getTweetsByHashTag(formatLabel);
    }

}
