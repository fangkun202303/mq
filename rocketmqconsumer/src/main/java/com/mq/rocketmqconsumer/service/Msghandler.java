package com.mq.rocketmqconsumer.service;

import com.mq.rocketmqconsumer.domain.Orders;

/**
 * @description: 消息处理的demo
 * @program: rocketmqconsumer
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-28
 */
public interface Msghandler {

    public void msgHandler(String msg);

    public Integer insertOneOrder(Orders orders);
}
