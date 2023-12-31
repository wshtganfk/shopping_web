package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.dto.common.LoginRequest;
import com.bfs.shopping_web.exception.GlobalException;
import com.bfs.shopping_web.security.AuthUserDetail;
import com.bfs.shopping_web.security.JwtProvider;
import com.bfs.shopping_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;


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

    @CrossOrigin
    @PostMapping("/login")
    public DataResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

        } catch (AuthenticationException e){
            e.printStackTrace();
//            throw new BadCredentialsException("Provided credential is invalid.");
            return DataResponse.builder()
                    .message("Provided credential is invalid")
                    .success(false)
                    .token(null)
                    .build();

        }
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();
        String token = jwtProvider.createToken(authUserDetail);


        DataResponse response = DataResponse.builder()
                .message("Successfully Authenticated")
                .success(true)
                .token(token)
                .build();

        return response;
    }
    @CrossOrigin
    @PostMapping
    public DataResponse register(@RequestBody User user)throws GlobalException {
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
            System.out.println("register success");
            return DataResponse.builder()
                    .message("success")
                    .success(true)
                    .data(userService.addUser(user))
                    .build();
        }


    }

}
