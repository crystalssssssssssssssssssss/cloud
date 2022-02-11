package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope   //实现远程云端配置文件动态刷新,注意此配置需要运维人员手动发送curl -X POST "http://localhost:3355/actuator/refresh"才能生效，可以避免重启
public class ConfigClientController {
    /**
     * 远程获取主配置信息
     */
    @Value("${config.info}")
    private String configInfo;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return serverPort+","+configInfo;
    }

}
