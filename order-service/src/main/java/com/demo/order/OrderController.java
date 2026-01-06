package com.demo.order;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.demo.common.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    @SentinelResource(value = "getOrder", blockHandler = "handleBlock")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    public Order handleBlock(Long id, BlockException ex) {
        return new Order(-1L, "限流/降级处理");
    }

    @PostMapping("/create")
    @SentinelResource(value = "createOrder", blockHandler = "createOrderBlock")
    public String createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return "success";
    }

    public String createOrderBlock(Order order, BlockException ex) {
        return "限流/降级处理";
    }
}
