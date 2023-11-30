package com.bfs.shopping_web.controller;

import com.bfs.shopping_web.domain.NewOrder;
import com.bfs.shopping_web.domain.Order;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.dto.common.DataResponse;
import com.bfs.shopping_web.service.OrderService;
import com.bfs.shopping_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @CrossOrigin
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('user')")
    public DataResponse getAllOrdersByUser() {
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUsername(username).get();
        if(user.getRole() == 1) return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(orderService.getAllOrders())
                .build();

        else return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(orderService.getOrdersByUserId(user.getUser_id()))
                .build();
    }

    @CrossOrigin
    @PostMapping
    @ResponseBody
    public DataResponse addOrder(@RequestBody Object object){
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUsername(username).get();

        LinkedHashMap<Integer, List<LinkedHashMap<String,Integer>>> newOrder = (LinkedHashMap<Integer, List<LinkedHashMap<String,Integer>>> ) object;
        List<NewOrder> newOrders = new ArrayList<>();
        for (Map.Entry<Integer, List<LinkedHashMap<String,Integer>>>entry : newOrder.entrySet()){
            System.out.println(entry.getKey());
            List<LinkedHashMap<String,Integer>> temp = entry.getValue();

            for(Map map : temp){
                NewOrder temp1 = new NewOrder();
                System.out.println(map.get("productId"));
                System.out.println(map.get("quantity"));
                temp1.setProductId((Integer) map.get("productId"));
                temp1.setQuantity((Integer) map.get("quantity"));
                newOrders.add(temp1);
            }

        }
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(orderService.addOrder(newOrders, user))
                .build();

    }

    @CrossOrigin
    @GetMapping("/{id}")
    @ResponseBody
    public DataResponse getOrderById(@PathVariable("id") int id) {

        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(orderService.getOrdersById(id))
                .build();
    }

    @CrossOrigin
    @PatchMapping("/{orderId}")
    @ResponseBody
    public DataResponse updateOrderById(@PathVariable("orderId") Long orderId, @RequestParam String status) {
        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUsername(username).get();
        Order order = orderService.getOrdersById(orderId);

        String currentStatue = order.getOrder_status();

         if (status.equals("cancel") && !status.equals(currentStatue)) {
            order.setOrder_status("cancel");

            return DataResponse.builder()
                    .success(true)
                    .message("done")
                    .data(orderService.updateOrder(order, status))
                    .build();
        } else if(status.equals("complete") && !status.equals(currentStatue)){
            order.setOrder_status("complete");
            if(user.getRole() == 0)
                return DataResponse.builder()
                        .success(false)
                        .message("Seller only")
                        .build();
            else
                return DataResponse.builder()
                    .success(true)
                    .message("done")
                    .data(orderService.updateOrder(order, status))
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
