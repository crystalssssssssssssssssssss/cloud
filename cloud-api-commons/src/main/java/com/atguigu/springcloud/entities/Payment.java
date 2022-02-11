package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * lombok注解化编程
 */
@Data //setter和getter
@AllArgsConstructor//全部参数的构造函数
@NoArgsConstructor//无参构造函数
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
