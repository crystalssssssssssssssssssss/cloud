package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoaderBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //远程服务调用地址
    public final static String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    //远程服务调用者
    @Resource
    private RestTemplate restTemplate;
    //引入自己手写的负载均衡类
    @Resource
    private LoaderBalancer loaderBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 消费者一般用Get请求
     * @param payment
     * @return
     */
    @GetMapping("/consumer/payment/create")
    public CommonResult<Integer> create(Payment payment){
        //发送post请求，请求参数是payment，返回数据是CommonResult对象
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    /**
     *  getForObject/postForObject：返回对象为响应体中数据转化成的对象，基本上可以理解为json
     *  getForEntity/postForEntity：返回对象为ResponseEntity对象，包含了响应中的一些信息，比如响应头、响应状态码，响应体等
     */
    @GetMapping("/consumer/paymentForEntity/create")
    public CommonResult<Integer> create2(Payment payment){
        //发送post请求，请求参数是payment，返回数据是CommonResult对象
        return restTemplate.postForEntity(PAYMENT_URL+"/payment/create",payment,CommonResult.class).getBody();
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        //发送get请求，返回数据是CommonResult对象
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){
        //发送get请求，返回数据是CommonResult对象
        ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if(forEntity.getStatusCode().is2xxSuccessful()){
            return forEntity.getBody();
        }else{
            return new CommonResult<>(444,"查询失败");
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if(instances == null | instances.size() <= 0){
            return null;
        }

        ServiceInstance serviceInstance = loaderBalancer.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }

    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        String result = restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin",String.class);
        return result;
    }




}
