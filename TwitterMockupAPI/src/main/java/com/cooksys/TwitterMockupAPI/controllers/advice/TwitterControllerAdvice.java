package com.cooksys.TwitterMockupAPI.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cooksys.TwitterMockupAPI.dtos.ErrorDto;
import com.cooksys.TwitterMockupAPI.exceptions.BadRequestException;
import com.cooksys.TwitterMockupAPI.exceptions.NotAuthorizedException;
import com.cooksys.TwitterMockupAPI.exceptions.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = {"com.cooksys.TwitterMockupAPI.controllers"})
@ResponseBody
public class TwitterControllerAdvice {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDto handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException){
        return new ErrorDto(badRequestException.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handleNotFoundException(HttpServletRequest request, NotFoundException notFoundException){
        return new ErrorDto(notFoundException.getMessage());
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotAuthorizedException.class)
    public ErrorDto handleNotAuthorizedException(HttpServletRequest request, NotAuthorizedException notAuthorizedException){
        return new ErrorDto(notAuthorizedException.getMessage());
    }

}
