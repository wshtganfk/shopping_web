package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.dto.question.QuestionCreationRequest;
import com.bfs.shopping_web.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
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

//    @PostMapping("/question")
//    @ResponseBody
//    public DataResponse addProduct(@Valid @RequestBody QuestionCreationRequest request, BindingResult result) {
//
//        if (result.hasErrors()) return DataResponse.builder()
//                                            .success(false)
//                                            .message("Something went wrong")
//                                            .build();
//
//        User user = User.builder()
//                .description(request.getDescription())
//                .isActive(request.isActive())
//                .build();
//
//        questionService.addQuestion(user);
//
//        return DataResponse.builder()
//                .success(true)
//                .message("Success")
//                .build();
//    }
}
