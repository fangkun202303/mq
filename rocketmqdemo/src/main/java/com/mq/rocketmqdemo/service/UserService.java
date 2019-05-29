package com.mq.rocketmqdemo.service;

import com.mq.rocketmqdemo.domain.User;

/**
 * @description: 模拟rocketmq的事务消息的服务接口例子
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-29
 */
public interface UserService {

    public Integer insertOneUser(User user);
}
