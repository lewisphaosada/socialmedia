package com.cooksys.TwitterMockupAPI.services.impl;

import com.cooksys.TwitterMockupAPI.dtos.*;
import com.cooksys.TwitterMockupAPI.entities.Tweet;
import com.cooksys.TwitterMockupAPI.entities.User;
import com.cooksys.TwitterMockupAPI.entities.embeddables.Credentials;
import com.cooksys.TwitterMockupAPI.entities.embeddables.Profile;
import com.cooksys.TwitterMockupAPI.exceptions.BadRequestException;
import com.cooksys.TwitterMockupAPI.exceptions.NotFoundException;
import com.cooksys.TwitterMockupAPI.mappers.CredentialsMapper;
import com.cooksys.TwitterMockupAPI.mappers.ProfileMapper;
import com.cooksys.TwitterMockupAPI.mappers.TweetMapper;
import com.cooksys.TwitterMockupAPI.mappers.UserMapper;
import com.cooksys.TwitterMockupAPI.repositories.TweetRepository;
import com.cooksys.TwitterMockupAPI.repositories.UserRepository;
import com.cooksys.TwitterMockupAPI.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final ProfileMapper profileMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<UserResponseDto> getAllActiveUsers(){
        List<User> users = userRepository.getActiveUsers();
        return userMapper.entitiesToResponseDtos(users);
    }

@Override
public UserResponseDto createUser(UserRequestDto userRequestDto){


    if(userRequestDto.getProfile() == null || userRequestDto.getCredentials() == null){
        throw new BadRequestException("Profile or Credentials cannot be null");
    }

    if(userRequestDto.getCredentials().getUsername() == null ||
            userRequestDto.getCredentials().getPassword() == null ||
            userRequestDto.getProfile().getEmail() == null ||
            userRequestDto.getCredentials().getPassword().equals("")){
        throw new BadRequestException("Username, password, or email cannot be null");
    }

    User createdUser = new User();
//    Credentials userCreds = new Credentials();
//    userCreds.setUsername(userRequestDto.getCredentials().getUsername());
//    userCreds.setPassword(userRequestDto.getCredentials().getPassword());
    Optional<User> existingUser = userRepository.findByCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));

    if(existingUser.isPresent()){
        createdUser = existingUser.get();
        createdUser.setDeleted(false);
    } else {
        createdUser = userMapper.requestDtoToEntity(userRequestDto);
    }

    if(existingUser.isPresent()){
        throw new BadRequestException("User already exists");
    }


        userRepository.saveAndFlush(createdUser);
    return userMapper.entityToDto(createdUser);
}

@Override
public UserResponseDto updateUser(String username, UserRequestDto userRequestDto){

        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

   if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
       throw new NotFoundException("User does not exist or is deleted.");
   }

   User existingUser = optionalUser.get();

    if (userRequestDto.getCredentials() == null ||
            !userRequestDto.getCredentials().getUsername().equals(existingUser.getCredentials().getUsername()) ||
            !userRequestDto.getCredentials().getPassword().equals(existingUser.getCredentials().getPassword())) {
        throw new BadRequestException("Credentials do not match.");
    }

//   Profile updatedProfile = profileMapper.dtoToEntity(userRequestDto.getProfile());
//   existingUser.setProfile(updatedProfile);
    // this is a post method overriding whole profile when we need to do individually for each field. use if block

    if (userRequestDto.getProfile() != null) {
        Profile existingProfile = existingUser.getProfile();

        if (userRequestDto.getProfile().getFirstName() != null && !userRequestDto.getProfile().getFirstName().isEmpty()) {
            existingProfile.setFirstName(userRequestDto.getProfile().getFirstName());
        }

        if (userRequestDto.getProfile().getLastName() != null && !userRequestDto.getProfile().getLastName().isEmpty()) {
            existingProfile.setLastName(userRequestDto.getProfile().getLastName());
        }

        if (userRequestDto.getProfile().getEmail() != null && !userRequestDto.getProfile().getEmail().isEmpty()) {
            existingProfile.setEmail(userRequestDto.getProfile().getEmail());
        } else {
            throw new BadRequestException("Email cannot be null or empty.");
        }

        if (userRequestDto.getProfile().getPhone() != null && !userRequestDto.getProfile().getPhone().isEmpty()) {
            existingProfile.setPhone(userRequestDto.getProfile().getPhone());
        }
    }


   User updatedUser = userRepository.save(existingUser);
   return userMapper.entityToDto(updatedUser);
}

@Override
public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto){

    Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

    if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
        throw new NotFoundException("User does not exist");
    }

    if(credentialsDto.getUsername() == null ||
            credentialsDto.getPassword() == null ||
            !credentialsDto.getPassword().equals(optionalUser.get().getCredentials().getPassword())||
    !credentialsDto.getUsername().equals(optionalUser.get().getCredentials().getUsername())){
        throw new BadRequestException("Credentials do not match");
    }

    User toDeleteUser = optionalUser.get();
    toDeleteUser.setDeleted(true);
    User deleteUser = userRepository.save(toDeleteUser);
    return userMapper.entityToDto(deleteUser);
}

@Override
public UserResponseDto followUser(String username, CredentialsDto credentialsDto){

Optional<User> optionalUser = userRepository.findByCredentialsUsername(credentialsDto.getUsername());

if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
    throw new NotFoundException("User does not exist");
}

if(!credentialsDto.getPassword().equals(optionalUser.get().getCredentials().getPassword())){
        throw new BadRequestException("Credentials do not match");
    }

Optional<User> userToFollow = userRepository.findByCredentialsUsername(username);

if(userToFollow.isEmpty() || userToFollow.get().isDeleted()){
    throw new NotFoundException("Can't follow non-existent user");
}

User follower = optionalUser.get();
User followingThisPerson= userToFollow.get();

if(follower.getFollowing().contains(followingThisPerson)){
    throw new BadRequestException("Already following");
}

follower.getFollowers().add(followingThisPerson);
followingThisPerson.getFollowers().add(follower);
userRepository.save(follower);
userRepository.save(followingThisPerson);

return userMapper.entityToDto(follower);

}

    @Override
    public List<TweetResponseDto> getFeed(String username){

    Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

    if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
        throw new NotFoundException("User does not exist/is deleted");
    }

    User userFeed = optionalUser.get();

    List<Tweet> tweetList = tweetRepository.findByAuthor_IdAndDeletedFalseOrderByPostedDesc(userFeed.getId());

    List<Tweet> followingTweets = new ArrayList<>();

    for(User followingUser: userFeed.getFollowing()){
        followingTweets.addAll(tweetRepository.findByAuthor_IdAndDeletedFalseOrderByPostedDesc(followingUser.getId()));
    }

    List<Tweet> allTweets = new ArrayList<>();
    allTweets.addAll(tweetList);
    allTweets.addAll(followingTweets);


    return tweetMapper.entitiesToResponseDtos(allTweets);
    //needs to be in reverse
    }

    @Override
public List<TweetResponseDto> getUserTweets(String username){
   Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

     if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
            throw new NotFoundException("User not found");
        }


List<Tweet> userTweets = tweetRepository.nonDeletedTweets();

return tweetMapper.entitiesToResponseDtos(userTweets);

}

@Override
public List<UserResponseDto> getUserFollowing(String username){

    Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

    if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
        throw new NotFoundException("User not found");
    }
    User user = optionalUser.get();
    List<User> userFollowing = userRepository.findByFollowersIdAndDeletedFalse(user.getId());

    if(userFollowing.isEmpty()){
        throw new NotFoundException("User not following anyone");
    }

        return userMapper.entitiesToResponseDtos(userFollowing);
}

    @Override
    public UserResponseDto getUser(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

        if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
            throw new NotFoundException("User not found");
        }

        User existingUser = optionalUser.get();
        return userMapper.entityToDto(existingUser);
    }

    @Override
    public List<UserResponseDto> getUserFollowers(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

        if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
            throw new NotFoundException("User not found");
        }
        //is this correct: findByFollowersIdAndDeletedFalse?  confirmed works
        User user = optionalUser.get();
        List<User> userFollowers = userRepository.findByFollowingIdAndDeletedFalse(user.getId());

        if(userFollowers.isEmpty()){
            throw new NotFoundException("User has no followers");
        }

        return userMapper.entitiesToResponseDtos(userFollowers);
    }
    @Override
    public List<TweetResponseDto> getMentions(String username) {
        //check if user exists
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

        if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
            throw new NotFoundException("User not found");
        }

        List<Tweet> tweets = userRepository.findMentions(username);

        return tweetMapper.entitiesToResponseDtos(tweets);


    }

    @Override
    public UserResponseDto unFollowUser(String username, CredentialsDto credentialsDto) {
        //check if there is a prexisting following relationship between the two users
        //if no prexisitng following, or no user exists, or credentails dont match, send an error.

        //get url username
        Optional<User> userToUnFollow = userRepository.findByCredentialsUsername(username);

        //check if url username exists in the database
        if(userToUnFollow.isEmpty() || userToUnFollow.get().isDeleted()){
            throw new NotFoundException("User not found");
        }

        //get the username from the requestbody
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(credentialsDto.getUsername());

        //check if the credentials username exists
        if(optionalUser.isEmpty() || optionalUser.get().isDeleted()){
            throw new NotFoundException("wrong credentials try again: + " + optionalUser);
        }
        //check password creds
        if(!credentialsDto.getPassword().equals(optionalUser.get().getCredentials().getPassword())){
            throw new BadRequestException("Credentials password do not match");
        }


        User unFollow = optionalUser.get();
        User unFollowingThisPerson = userToUnFollow.get();

        if(!unFollow.getFollowing().contains(unFollowingThisPerson)){
            throw new BadRequestException("You aren't following this person");
        }

        unFollow.getFollowing().remove(unFollowingThisPerson);
        unFollowingThisPerson.getFollowing().remove(unFollow);
        userRepository.save(unFollow);
        userRepository.save(unFollowingThisPerson);

        return userMapper.entityToDto(unFollow);

    }

}
