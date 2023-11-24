package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.domain.Order;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping
    @ResponseBody
    public DataResponse getAllOrdersByUser() {
        User user = new User();
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(orderService.getOrdersByUserId(user.getUser_id()))
                .build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public DataResponse getOrderById(@PathVariable("id") int id) {
        User user = new User();
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(orderService.getOrdersById(id))
                .build();
    }
    @PatchMapping("/{orderId}")
    @ResponseBody
    public DataResponse cancelOrderById(@PathVariable("orderId") Long orderId, @RequestParam String status) {
        User user = new User();
        Order order = orderService.getOrdersById(orderId);
        String currentStatue = order.getOrder_status();

         if (status.equals("cancel") && !status.equals(currentStatue)) {
            order.setOrder_status("cancel");
            return DataResponse.builder()
                    .success(false)
                    .message("done")
                    .data(orderService.updateOrder(order))
                    .build();
        } else if(status.equals("complete") && !status.equals(currentStatue)){
            order.setOrder_status("complete");
            return DataResponse.builder()
                    .success(false)
                    .message("done")
                    .data(orderService.updateOrder(order))
                    .build();
        }else {
            return DataResponse.builder()
                    .success(false)
                    .message("order is already " + status)
                    .data(order)
                    .build();
        }


    }

}
