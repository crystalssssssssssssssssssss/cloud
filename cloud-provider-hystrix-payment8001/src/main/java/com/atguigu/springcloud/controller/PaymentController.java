package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_Ok(@PathVariable("id") Integer id){
       String res =  paymentService.paymentInfo_Ok(id);
       log.info("*****res:"+res);
       return res;
    }

    /**
     * 可以采取 jmeter对下面这个超时服务进行高并发压测，结论是其他的微服务也被拖慢了（tomcat线程数被用完了，分不出来处理其他微服务）
     * @param id
     * @return
     */
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
        String res =  paymentService.paymentInfo_Timeout(id);
        log.info("*****res:"+res);
        return res;
    }

    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String res = paymentService.paymentCircuitBreaker(id);
        log.info("****result:"+res);
        return res;
    }
}
