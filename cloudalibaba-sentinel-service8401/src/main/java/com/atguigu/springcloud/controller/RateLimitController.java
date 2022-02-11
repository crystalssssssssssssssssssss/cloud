package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")//sentinel监控byResource资源
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名称限流测试ok",new CommonResult(2020,"serial001"));
    }

    public CommonResult handleException(BlockException exception){
        return new CommonResult(444,exception.getClass().getCanonicalName()+"\t"+"服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl") //采取sentinel自带的兜底方案
    public CommonResult byUrl(){
        return new CommonResult(200,"按url限流测试ok",new CommonResult(2020,"serial002"));
    }

    @GetMapping("/rateLimit/customerBlockHandler/{id}")
    @SentinelResource(value = "CustomerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,//处理sentinel的配置违规限流类
            blockHandler = "handlerException2", //处理sentinel的配置违规限流方法
//            fallbackClass =      //处理处理运行时异常类
            fallback = "RuntimeExceptionHandler" //处理处理运行时异常兜底方法
//            exceptionsToIgnore = IllegalArgumentException.class  //可以忽略某个异常类
            )
    public CommonResult customerBlockHandler(@PathVariable("id") Integer id){
        if(id ==4){
            throw new IllegalArgumentException("非法参数异常");
        }
        return new CommonResult(200,"按用户自定义",new CommonResult(2020,"serial003"));
    }

    /**
     * 处理运行时异常的兜底方法
     * @param id
     * @return
     */
    public  CommonResult RuntimeExceptionHandler(@PathVariable("id") Integer id){
        return new CommonResult(5555,"运行时异常");
    }
}
