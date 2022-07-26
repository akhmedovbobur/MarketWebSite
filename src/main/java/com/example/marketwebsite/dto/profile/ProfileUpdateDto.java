package com.example.marketwebsite.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ProfileUpdateDto{

        @NotEmpty(message = "Please provide a name")
        private String name;
        @NotBlank(message = "Please provide a surname")
        private String surname;
        @NotEmpty(message = "Please provide a contact")
        private String contact;
        @NotEmpty(message = ("Image id error"))
        private Integer imageId;
}
