package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回前端的通用json字符串（前后端分离）
 * @param <T>
 */
@Data //setter和getter
@AllArgsConstructor//全部参数的构造函数
@NoArgsConstructor//无参构造函数
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code,message,null);
    }
}
