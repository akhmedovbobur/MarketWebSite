package com.example.marketwebsite.dto.profile;

import com.example.marketwebsite.dto.AddressDto;
import com.example.marketwebsite.enums.ProfileRole;
import com.example.marketwebsite.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDetailDto {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String contact;
    private ProfileStatus status;
    private String password;
    private Integer imageId;
    private ProfileRole role;
    private LocalDateTime createdDate;
    private String token;
    private AddressDto address;
}
