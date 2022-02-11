package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")  //value为远程服务的名称
public interface PaymentFeignService {
    @GetMapping("/payment/get/{id}")//远程服务的地址
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/timeout")
    public String getPaymentTimeout();
}
