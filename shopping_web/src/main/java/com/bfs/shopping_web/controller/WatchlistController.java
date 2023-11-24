package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.dao.WatchlistDao;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {
    @Autowired
    WatchlistService watchlistService;

    @GetMapping("/products")
    @ResponseBody
    public DataResponse getUserWatchList(){
        User user = new User();
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.getUserWatchList(user))
                .build();
    }

    @DeleteMapping("/products")
    @ResponseBody
    public DataResponse deleteUserWatchList(@PathVariable int prodcut_id){
        User user = new User();
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.deleteUserWatchList(user, prodcut_id))
                .build();
    }

    @PostMapping("/products")
    @ResponseBody
    public DataResponse addUserWatchList(@RequestBody Product product){
        User user = new User();
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(watchlistService.addUserWatchList(user, product.getProduct_id()))
                .build();
    }
}
