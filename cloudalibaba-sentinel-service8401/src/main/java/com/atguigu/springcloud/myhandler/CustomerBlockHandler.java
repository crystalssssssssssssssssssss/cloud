package com.atguigu.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import org.springframework.web.bind.annotation.PathVariable;

public class CustomerBlockHandler {
    /**
     * 注意这里的兜底方法都必须是静态方法，即static修饰的
     */
    public static CommonResult handlerException1(BlockException exception){
        return new CommonResult(4444,"按客户自定义，global handlerException11111");
    }

    public static CommonResult handlerException2(@PathVariable("id") Integer id,BlockException exception){
        return new CommonResult(4444,"按客户自定义，global handlerException22222");
    }

//    public static CommonResult RuntimeExceptionHandler(@PathVariable("id") Integer id,BlockException exception){
//        return new CommonResult(5555,"运行时异常");
//    }
}
