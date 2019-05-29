package com.mq.rocketmqconsumer.service.impl;

import com.mq.rocketmqconsumer.mapper.OrderMapper;
import com.mq.rocketmqconsumer.domain.Orders;
import com.mq.rocketmqconsumer.service.Msghandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 消息处理的实现类
 * @program: rocketmqconsumer
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-28
 */
@Service
public class MsghandlerImpl implements Msghandler {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void msgHandler(String msg) {
        System.out.println("=================>>>>>>>>>>>>>>>>>>"+msg);
    }

    /**
     * Description: 收到通知后向订单表插入一条数据
     *
     * @author fangkun
     * @param orders
     * @return void
     * @createDate 2019/5/29 17:06
     **/
    @Transactional
    @Override
    public Integer insertOneOrder(Orders orders) {
        return orderMapper.insertOneOrder(orders);
    }
}
