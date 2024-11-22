package com.cooksys.TwitterMockupAPI.controllers;


import com.cooksys.TwitterMockupAPI.dtos.*;
import com.cooksys.TwitterMockupAPI.entities.Tweet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.TwitterMockupAPI.services.TweetService;

import lombok.RequiredArgsConstructor;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    //get tweets
    @GetMapping
    public List<TweetResponseDto> getAllTweets(){
        return tweetService.getAllTweets();
    }

    //get tweet by id
    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id){
        return tweetService.getTweetById(id);
    }


    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> mentionedUsers(@PathVariable Long id){
        return tweetService.mentionedUsers(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getTweetReplies(@PathVariable Long id){
        return tweetService.getTweetReplies(id);
    }

    @PostMapping("/{id}/repost")
    public TweetResponseDto repostTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.repostTweet(id, tweetRequestDto);
    }

    //get tags associated with tweet id
    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTagById(@PathVariable Long id){
        return tweetService.getTagById(id);
    }

    //get reposts of a tweet
    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getReposts(@PathVariable Long id){
        return tweetService.getReposts(id);
    }

    //get the likes
    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getLikes(@PathVariable Long id){
        return tweetService.getLikes(id);
    }

    //context of tweet before and after, excluding deleted tweets
    @GetMapping("/{id}/context")
    public ContextDto getContext(@PathVariable Long id){
        return tweetService.getContext(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto){
        return tweetService.deleteTweet(id, credentialsDto);
    }

    //posting, prase the hashtag in the content of the tweet, beware of mentions
    @PostMapping
    public TweetResponseDto postTweet(@RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.postTweet(tweetRequestDto);
    }

    @PostMapping("/{id}/like")
    public void postLike(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto){
        tweetService.postLike(id, credentialsDto);
    }

    @PostMapping("/{id}/reply")
    public TweetResponseDto postReply(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.postReply(id, tweetRequestDto);
    }
}
