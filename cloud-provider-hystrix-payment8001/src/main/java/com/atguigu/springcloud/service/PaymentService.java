package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentService {

    public String paymentInfo_Ok(Integer id){
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_ok:"+id+"\t"+"ok!";
    }

    /**
     * @HystrixCommand：超时 服务降级处理
     *
     */

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_Timeout(Integer id ){
        int timeNumber = 5;
        try {
            Thread.sleep(   timeNumber*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        int a = 10/0;
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_Timeout:"+id+"\t"+"耗时："+timeNumber;
    }

    /**
     * fallback兜底方法
     * 上面方法故意制造的两个异常：超时异常（最多响应3秒，但需要5秒），计算异常 执行后都会让服务不可用
     * 所有做服务降级，兜底的方法都是paymentInfo_TimeoutHandler
     */

    public String paymentInfo_TimeoutHandler(Integer id ){
        return "线程池："+Thread.currentThread().getName()+" 8001系统忙或运行报错，请稍后再试:"+id+"\t"+"😭";
    }

    /**
     * 服务熔断 断路器closed---》open-----》halfOpen-----》closed
     *             正常访问---》不能访问---》尝试访问----》 恢复正常访问
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("****** id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"/t"+"调用成功，流水号："+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍后再试，😭id:"+id;
    }


}
