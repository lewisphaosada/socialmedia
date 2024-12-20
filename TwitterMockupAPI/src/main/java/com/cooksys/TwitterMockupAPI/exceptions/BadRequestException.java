package com.cooksys.TwitterMockupAPI.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException{

    private static final long serialVersionUID = 6580296965767412034L;

    private String message;
}
