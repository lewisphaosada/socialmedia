package com.cooksys.TwitterMockupAPI.services;

import org.springframework.web.bind.annotation.GetMapping;

public interface ValidateService {

    boolean getHashtagExists(String label);

   boolean userExists(String username);

    boolean isUserNameAvailable(String username);

}
