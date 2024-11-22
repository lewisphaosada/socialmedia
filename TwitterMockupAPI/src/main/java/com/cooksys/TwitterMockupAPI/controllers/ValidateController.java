package com.cooksys.TwitterMockupAPI.controllers;

import com.cooksys.TwitterMockupAPI.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("/tag/exists/{label}")
    public boolean getHashtagExists(@PathVariable String label){
        return validateService.getHashtagExists(label);
    }

    @GetMapping("/username/exists/{username}")
    public boolean userExists(@PathVariable String username){
        return validateService.userExists(username);
    }

    @GetMapping("/username/available/{username}")
    public boolean isUserNameAvailable(@PathVariable String username){
    return validateService.isUserNameAvailable(username);
    }


}
