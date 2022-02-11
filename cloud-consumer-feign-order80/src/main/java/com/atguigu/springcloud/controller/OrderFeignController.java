package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
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
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return paymentFeignService.getPayment(id);
    }

    @GetMapping(value = "/consumer/payment/timeout")
    public String getPaymentTimeout(){
        //feign默认等待服务器响应1秒钟，超时报错
        return paymentFeignService.getPaymentTimeout();
    }

}
