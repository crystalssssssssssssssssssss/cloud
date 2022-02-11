package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoaderBalancer {
    public ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
