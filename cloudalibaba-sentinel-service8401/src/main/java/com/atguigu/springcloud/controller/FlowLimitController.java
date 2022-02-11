package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        return "----------testA";
    }

    @GetMapping("/testB")
    public String testB(){
        return "----------testB";
    }

    /**
     * @RequestParam 这个注解不是必须的
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")//类似于HystrixCommand注解
    public String testHotKey(@RequestParam(value ="p1",required = false)String p1,
                             @RequestParam(value ="p2",required = false)String p2){
        return "testHotKey...";
    }

    //兜底方法
    public String deal_testHotKey(String p1, String p2, BlockException exception){
        return "deal_testHotKey....,😭";
    }
}
