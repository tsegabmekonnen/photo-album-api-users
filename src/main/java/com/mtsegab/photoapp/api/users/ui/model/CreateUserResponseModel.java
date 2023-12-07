package com.mtsegab.photoapp.api.users.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;

    @Override
    public String toString(){
        return "firstName : " + firstName + " lastName : " + lastName + " email : " + email;
    }

}
