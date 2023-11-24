package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.dto.common.LoginRequest;
import com.bfs.shopping_web.security.AuthUserDetail;
import com.bfs.shopping_web.security.JwtProvider;
import com.bfs.shopping_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
    private AuthenticationManager authenticationManager;
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public DataResponse login(@RequestBody LoginRequest request) {
        Authentication authentication;
        try{
            System.out.println(request.getUsername());
            System.out.println(request.getPassword());
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e){
            System.out.println("in catch");
            e.printStackTrace();
            throw new BadCredentialsException("Provided credential is invalid.");
        }
        System.out.println(authentication.isAuthenticated());

        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();

        String token = jwtProvider.createToken(authUserDetail);
        System.out.println(token);

        DataResponse response = DataResponse.builder()
                .message("Successfully Authenticated")
                .success(true)
                .token(token)
                .build();

        return response;
    }

    @PostMapping
    public DataResponse register(@RequestBody User user){
        System.out.println("in controller");
        if(user.equals(null))
            return DataResponse.builder()
                .message("need user info")
                .success(false)
                .build();
        else if (userService.getUserByEmail(user.getEmail()).isPresent() || userService.getUserByUsername(user.getUsername()).isPresent()){
            System.out.println(userService.getUserByEmail(user.getEmail()).isPresent());
            return DataResponse.builder()
                    .message("user email/username already exist")
                    .success(false)
                    .build();
        }

        else {
            userService.addUser(user);
            System.out.println("register success");
            return DataResponse.builder()
                    .message("success")
                    .success(true)
                    .build();
        }


    }

}
