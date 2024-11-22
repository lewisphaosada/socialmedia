package com.cooksys.TwitterMockupAPI.services.impl;

import com.cooksys.TwitterMockupAPI.repositories.HashtagRepository;
import com.cooksys.TwitterMockupAPI.repositories.UserRepository;
import com.cooksys.TwitterMockupAPI.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public boolean getHashtagExists(String label){
        String hashtagLabel = "#" + label;
    return hashtagRepository.existsByLabel(label);
    }

    @Override
    public boolean userExists(String username) {
      return userRepository.existsByCredentialsUsername(username);
    }

    @Override
    public boolean isUserNameAvailable(String username){
        return !userRepository.existsByCredentialsUsername(username);
    }
}
