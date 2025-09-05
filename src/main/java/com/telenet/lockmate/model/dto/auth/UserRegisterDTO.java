package com.telenet.lockmate.model.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterDTO {

    private String fullName;
    private String emailAddress;
    private String physicalAddress;
    private String phoneNumber;
    
    private String password;
}