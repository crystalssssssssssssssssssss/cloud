package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod") //调用默认的fallback方法
public class OrderHystrixController {

    /**
     * 通过feign远程调用服务，controller层调用service层服务更符合调用习惯
     * openfeign调用就是接口服务+注解
     */
    @Resource
    private PaymentHystrixService paymentService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") Integer id){
        return paymentService.paymentInfo_Ok(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })   //采取独享的fallback 方法
    @HystrixCommand  //采用通用的fallback方法
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
        return paymentService.paymentInfo_Timeout(id);
    }

    public String paymentInfoTimeoutHandler(@PathVariable("id") Integer id){
        return "我是消费者80，对方支付系统繁忙请10秒钟中后再试或者自己运行出差请检查😭。。。";
    }



    /**
     * 这个是全局的fallback方法
     * 通用的和独享的分开，避免了代码膨胀，合理减少了代码量
     * @return
     */
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试，😭";
    }



}
