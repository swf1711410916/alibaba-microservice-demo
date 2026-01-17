package com.demo.order.service;

import com.demo.common.dto.Order;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.seata.spring.annotation.GlobalTransactional;

@Service
public class OrderService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Transactional
    @GlobalTransactional(name = "create-order")
    public void createOrder(Order order) {
        rocketMQTemplate.convertAndSend("order-topic", order);
    }

    public Order getOrder(Long id) {
        return new Order(id, "SampleOrder");
    }
}
