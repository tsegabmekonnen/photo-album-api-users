package com.mtsegab.photoapp.api.users.shared;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;

    @Override
    public String toString(){
        return "firstName : " + firstName + " lastName : " + lastName + " email : " + email;
    }

}
