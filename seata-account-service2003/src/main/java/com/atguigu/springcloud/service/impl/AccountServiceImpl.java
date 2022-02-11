package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.AccountDao;
import com.atguigu.springcloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {


    @Resource
    private AccountDao accountDao;
    @Override
    public void decrease(Long userId, BigDecimal money) throws InterruptedException {
        log.info("开始扣减账户余额");
        /**
         * 模拟超时异常，全局事务回滚（feign的远程调用默认是1秒，超过就报错）
         * 测试之后发现，订单状态是0（未完成），但是库存和账户余额都已经扣款了。
         */

            Thread.sleep(10000);//10秒

        accountDao.decrease(userId,money);
        log.info("扣减账户余额结束");
    }
}
