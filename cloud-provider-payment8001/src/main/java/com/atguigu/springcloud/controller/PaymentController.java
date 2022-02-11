package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Timer;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping("/payment/discovery")
    public Object discover(){
        List<String> services = discoveryClient.getServices();
        for (String service:services
             ) {
            log.info(service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances
             ) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLb(){
        return serverPort;
    }

    @GetMapping(value = "/payment/timeout")
    public String getPaymentTimeout(){
        //暂停3秒钟
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return "hi ,i am paymentZipkin server fall back,welcome to atguigu,😊";
    }


}
