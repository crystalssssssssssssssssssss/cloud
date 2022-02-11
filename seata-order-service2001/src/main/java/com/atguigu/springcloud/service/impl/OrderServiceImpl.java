package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.OrderDao;
import com.atguigu.springcloud.domain.Order;
import com.atguigu.springcloud.service.AccountService;
import com.atguigu.springcloud.service.OrderService;
import com.atguigu.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;

    @Override
    //处理分布式事务
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)//设置全局事务回滚（任何错误都回滚）
    public void create(Order order) {
       log.info("----->开始新建订单");
       orderDao.create(order);

       log.info("----->订单微服务开始调用库存，做扣减数量");
       storageService.decrease(order.getProductId(),order.getCount());

       log.info("----->订单微服务开始调用账户，做扣减money");
       accountService.decrease(order.getUserId(),order.getMoney());

       log.info("----->开始修改订单状态开始 0-->1");
       orderDao.update(order.getUserId(),0);
       log.info("----->开始修改订单状态结束");

       log.info("over!");

    }
}
