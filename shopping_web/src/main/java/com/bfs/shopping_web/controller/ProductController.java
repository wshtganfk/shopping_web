package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.service.ProductService;
import com.bfs.shopping_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    @GetMapping
    @ResponseBody
    public DataResponse getAllProducts() {
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
    @PreAuthorize("hasAuthority('admin')")
    public DataResponse updateProduct(@PathVariable long id, @RequestBody Product product) {
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(productService.updateProduct(product, id))
                .build();
    }
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('admin')")
    public DataResponse addProduct( @RequestBody Product product) {
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(productService.addProduct(product))
                .build();
    }

    @GetMapping("/popular")
    @ResponseBody
    @PreAuthorize("hasAuthority('admin')")
    public DataResponse TopThreePopularProduct(@RequestParam ("limit") int limit){
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(productService.TopThreePopularProduct(limit))
                .build();
    }
    @GetMapping("/profit")
    @ResponseBody
    @PreAuthorize("hasAuthority('admin')")
    public DataResponse getMostProfitableProduct(@RequestParam ("limit") int limit){
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(productService.getMostProfitableProduct( limit))
                .build();
    }
    @GetMapping("/frequent")
    @ResponseBody
    @PreAuthorize("hasAuthority('user')")
    public DataResponse getMostFrequentlyPurchasedProduct(@RequestParam ("limit") int limit){
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUsername(username).get();
        List<Product> aa = productService.getMostFrequentlyPurchasedProduct(user, limit);
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(aa)
                .build();
    }
    @GetMapping("/recent")
    @ResponseBody
    @PreAuthorize("hasAuthority('user')")
    public DataResponse getMostRecentlyPurchasedProduct(@RequestParam ("limit") int limit){
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUsername(username).get();
        return DataResponse.builder()
                .success(true)
                .message("success")
                .data(productService.getMostRecentlyPurchasedProduct(user, limit))
                .build();
    }


}
