package com.ms.userservice.controllers;

import com.ms.userservice.dtos.UserRecordDto;
import com.ms.userservice.model.UserEntity;
import com.ms.userservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> saveUser(@RequestBody @Valid UserRecordDto userRecordDto){
        var userEntity = new UserEntity();
        BeanUtils.copyProperties(userRecordDto, userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userEntity));
    }

}
