package com.cooksys.TwitterMockupAPI.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
//The request is what I expect the client to send me
public class TweetRequestDto {

    private String content;

    private CredentialsDto credentials;

}
