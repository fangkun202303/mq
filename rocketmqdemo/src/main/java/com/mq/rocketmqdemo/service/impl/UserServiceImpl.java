package com.mq.rocketmqdemo.service.impl;

import com.mq.rocketmqdemo.domain.User;
import com.mq.rocketmqdemo.mapper.UserMapper;
import com.mq.rocketmqdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 模拟rocketmq的事务消息的服务接口例子
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * Description: 模拟插入一条记录,同时需要通知消费者向库中插入一条订单的记录
     *
     * @author fangkun
     * @param user
     * @return java.lang.Integer
     * @createDate 2019/5/29 16:50
     **/
    @Transactional
    @Override
    public Integer insertOneUser(User user) {
        return userMapper.insertOneUser(user);
    }
}
