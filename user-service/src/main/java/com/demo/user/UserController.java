package com.demo.user;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    @SentinelResource(value = "getUser", blockHandler = "handleBlock")
    public String getUser(@PathVariable Long id) {
        return "User-" + id;
    }

    public String handleBlock(Long id, BlockException ex) {
        return "限流/降级处理";
    }
}
