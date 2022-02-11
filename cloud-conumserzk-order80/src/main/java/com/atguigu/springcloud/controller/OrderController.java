package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    //远程服务调用地址
    public final static String INVOKE_URL = "http://cloud-provider-payment";

    //远程服务调用者
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zk")
    public String zk(){
        return restTemplate.getForObject(INVOKE_URL+"/payment/zk",String.class);
    }


}
