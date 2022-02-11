package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult<Integer> create(@RequestBody Payment payment){
        int res = paymentService.create(payment);
        if(res > 0){
            return new CommonResult<>(200,"插入数据成功！,serverPort:"+serverPort,res);
        }else{
            return new CommonResult<>(444,"插入数据失败！",null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if(payment != null){
            return new CommonResult<>(200,"查询数据成功！,serverPort:"+serverPort,payment);
        }else{
            return new CommonResult<>(444,"查询失败，查询id是："+id,null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLb(){
        return serverPort;
    }


}
