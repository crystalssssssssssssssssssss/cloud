package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注意自定义的负载均衡规则类，不要防止主启动类包及其子包下
 */
@Configuration
public class MySelfRule {


    @Bean
    public IRule myRule(){
        return new RandomRule();//负载均衡策略为：随机
    }
}
