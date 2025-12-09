package com.springboot_multithreading_example.springboot_multithreading_example.controller;

import com.springboot_multithreading_example.springboot_multithreading_example.entity.User;
import com.springboot_multithreading_example.springboot_multithreading_example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) {
        for(MultipartFile multipartFile : files) {
            userService.saveUser(multipartFile);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
        return  userService.findAllUsers().thenApply(ResponseEntity::ok);
    }


    @GetMapping(value = "/getUsersByThread", produces = "application/json")
    public  ResponseEntity getUsers(){
        CompletableFuture<List<User>> users1=userService.findAllUsers(); // starts async in thread userThread-1
        CompletableFuture<List<User>> users2=userService.findAllUsers(); // starts async in thread userThread-3
        CompletableFuture<List<User>> users3=userService.findAllUsers(); // starts async in thread userThread-5
        CompletableFuture.allOf(users1,users2,users3).join(); // waits for all three to complete
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
