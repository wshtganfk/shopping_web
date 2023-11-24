package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.dto.question.QuestionCreationRequest;
import com.bfs.shopping_web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/all")
    @ResponseBody
    public DataResponse getAllProducts() {
//        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(productService.getAllProducts())
                .build();
    }

    @GetMapping("{id}")
    @ResponseBody
    public DataResponse getProductById(@PathVariable int id) {
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(productService.getProductById(id))
                .build();
    }
    @PatchMapping("{id}")
    @ResponseBody
    public DataResponse updateProduct(@PathVariable int id, @RequestBody Product product) {
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(productService.updateProduct(product))
                .build();
    }
    @PostMapping
    @ResponseBody
    public DataResponse addProduct(@PathVariable int id, @RequestBody Product product) {
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(productService.addProduct(product))
                .build();
    }

    @GetMapping("/popular")
    @ResponseBody
    public DataResponse TopThreePopularProduct(@RequestParam ("limit") int limit){
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(productService.TopThreePopularProduct(limit))
                .build();
    }
    @GetMapping("/frequent")
    @ResponseBody
    public DataResponse getMostFrequentlyPurchasedProduct(@RequestParam ("limit") int limit){
        User user = new User();
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(productService.getMostFrequentlyPurchasedProduct(user))
                .build();
    }
    @GetMapping("/recent")
    @ResponseBody
    public DataResponse getMostRecentlyPurchasedProduct(@RequestParam ("limit") int limit){
        User user = new User();
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(productService.getMostRecentlyPurchasedProduct(user))
                .build();
    }


}
