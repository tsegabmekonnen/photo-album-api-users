package com.mtsegab.photoapp.api.users.ui.controllers;

import com.mtsegab.photoapp.api.users.service.UsersServiceImpl;
import com.mtsegab.photoapp.api.users.shared.UserDto;
import com.mtsegab.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.mtsegab.photoapp.api.users.ui.model.CreateUserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersServiceImpl userService;

    @GetMapping("/status/check")
    public String status() {
        return "Working";
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
//           , produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponseModel> createUser( @RequestBody CreateUserRequestModel userDetails) {
        System.out.println("     *********" + userDetails.toString());
        System.out.println("       ***** 1.2 ****" + userDetails.getFirstName());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        System.out.println("     ********* 2nd time calling **** " + userDetails.toString());

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        System.out.println("     ********* 3rd time calling **** " + userDto.toString());
        CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
        System.out.println("     ********* 4th time calling **** " + returnValue.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

}
