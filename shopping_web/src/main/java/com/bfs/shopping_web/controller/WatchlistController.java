package com.bfs.shopping_web.controller;


import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.domain.Watchlist;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.exception.GlobalException;
import com.bfs.shopping_web.service.UserService;
import com.bfs.shopping_web.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {
    @Autowired
    WatchlistService watchlistService;
    @Autowired
    UserService userService;
    @CrossOrigin
    @GetMapping("/products")
    @ResponseBody
    @PreAuthorize("hasAuthority('user')")
    public DataResponse getUserWatchList()throws GlobalException {
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        Optional<User> user = userService.getUserByUsername(username);
        if(user.isPresent()) return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.getUserWatchList(user.get()))
                .build();
        else return DataResponse.builder()
                .success(false)
                .message("user not exist")
                .data(null)
                .build();
    }
    @CrossOrigin
    @DeleteMapping("/products/{productId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('user')")
    public DataResponse deleteUserWatchList(@PathVariable int productId)throws GlobalException {
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        Optional<User> user = userService.getUserByUsername(username);
        if(user.isPresent()) return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.deleteUserWatchList(user.get(), productId))
                .build();
        else return DataResponse.builder()
                .success(false)
                .message("user not exist")
                .data(null)
                .build();
    }
    @CrossOrigin
    @PostMapping("/products")
    @ResponseBody
    @PreAuthorize("hasAuthority('user')")
    public DataResponse addUserWatchList(@RequestBody Object product) throws GlobalException {
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        Optional<User> user = userService.getUserByUsername(username);
        List<Product> watchlists = watchlistService.getUserWatchList(user.get());

        LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) product;
        long product_id = Long.parseLong(map.get("productId"));

        boolean watchlistExists = watchlists.stream()
                .anyMatch(e -> e.getProduct_id() == product_id);
        if(watchlistExists)  return DataResponse.builder()
                .success(false)
                .message("product already in watchlist")
                .data(null)
                .build();
        if(user.isPresent()) return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.addUserWatchList(user.get(), product_id))
                .build();
        else return DataResponse.builder()
                .success(false)
                .message("user not exist")
                .data(null)
                .build();
    }
}
