package com.cooksys.TwitterMockupAPI.services;

import com.cooksys.TwitterMockupAPI.dtos.*;
import com.cooksys.TwitterMockupAPI.entities.User;

import java.util.List;

public interface UserService {

   List<UserResponseDto> getAllActiveUsers();

   UserResponseDto createUser(UserRequestDto userRequestDto);

   UserResponseDto updateUser(String username, UserRequestDto userRequestDto);

   UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

   UserResponseDto followUser(String username, CredentialsDto credentialsDto);

   List<TweetResponseDto> getFeed(String username);

   List<TweetResponseDto> getUserTweets(String username);

   List<UserResponseDto> getUserFollowing(String username);

   UserResponseDto getUser(String username);

   List<UserResponseDto> getUserFollowers(String username);

   List<TweetResponseDto> getMentions(String username);

   UserResponseDto unFollowUser(String username, CredentialsDto credentialsDto);
}
