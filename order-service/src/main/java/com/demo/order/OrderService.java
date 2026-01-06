package com.demo.order;

import com.demo.common.dto.Order;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.seata.spring.annotation.GlobalTransactional;

@Service
public class OrderService {

    @Autowired
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
