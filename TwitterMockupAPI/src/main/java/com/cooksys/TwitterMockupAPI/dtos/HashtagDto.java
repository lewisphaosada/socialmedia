package com.cooksys.TwitterMockupAPI.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagDto {

    private Long id;
//    These fields should be returned as well  -KW
    private Timestamp firstUsed;
    private Timestamp lastUsed;

}
