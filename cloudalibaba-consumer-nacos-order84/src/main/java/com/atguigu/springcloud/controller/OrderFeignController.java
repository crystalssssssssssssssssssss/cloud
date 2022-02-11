package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {

    /**
     * 通过feign远程调用服务，controller层调用service层服务更符合调用习惯
     * openfeign调用就是接口服务+注解
     */

    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/payment/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return paymentService.getPayment(id);
    }


}
