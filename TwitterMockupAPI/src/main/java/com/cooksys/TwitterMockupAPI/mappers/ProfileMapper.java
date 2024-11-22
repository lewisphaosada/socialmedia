package com.cooksys.TwitterMockupAPI.mappers;

import com.cooksys.TwitterMockupAPI.dtos.ProfileDto;
import com.cooksys.TwitterMockupAPI.entities.embeddables.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel= "spring")
public interface ProfileMapper {

    Profile dtoToEntity(ProfileDto profileDto);

    ProfileDto entityToDto(Profile profile);
}
