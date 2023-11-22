package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.dto.common.LoginRequest;
import com.bfs.shopping_web.dto.common.LoginResponse;
import com.bfs.shopping_web.security.AuthUserDetail;
import com.bfs.shopping_web.security.JwtProvider;
import com.bfs.shopping_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.authentication.AuthenticationManager;



@Controller
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

    @PostMapping
    public DataResponse register(@RequestBody User user) {
        User newUser = userService.addUser(user);
        if(newUser.equals(null)) return DataResponse.builder()
                .message("user exist with username/email")
                .success(false)
                .data(null)
                .build();
        else return DataResponse.builder()
                .message("user register success")
                .success(true)
                .data(newUser)
                .build();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        Authentication authentication;

        try{
            System.out.println("in try");
            System.out.println(request.getUsername());
            System.out.println(request.getPassword());
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e){
            System.out.println("in catch");
            throw new BadCredentialsException("Provided credential is invalid.");
        }
        System.out.println(authentication.isAuthenticated());

        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        String token = jwtProvider.createToken(authUserDetail);
        System.out.println(token);

        return LoginResponse.builder()
                .message("Successfully Authenticated")
                .token(token)
                .build();

    }

}
