package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @PostMapping("/account/decrease")
    public CommonResult decrease(Long userId, BigDecimal money) throws InterruptedException {
        accountService.decrease(userId,money);
        return new CommonResult(200,"扣款成功！");
    }
}
