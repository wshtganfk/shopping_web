package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.dao.WatchlistDao;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.service.UserService;
import com.bfs.shopping_web.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {
    @Autowired
    WatchlistService watchlistService;
    @Autowired
    UserService userService;

    @GetMapping("/products")
    @ResponseBody
    public DataResponse getUserWatchList(){
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUsername(username).get();
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.getUserWatchList(user))
                .build();
    }

    @DeleteMapping("/products/{productId}")
    @ResponseBody
    public DataResponse deleteUserWatchList(@PathVariable int productId){
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUsername(username).get();
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.deleteUserWatchList(user, productId))
                .build();
    }

    @PostMapping("/products")
    @ResponseBody
    public DataResponse addUserWatchList(@RequestBody Object product){
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUsername(username).get();

        LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) product;
        long product_id = Long.parseLong(map.get("productId"));
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.addUserWatchList(user, product_id))
                .build();
    }
}
