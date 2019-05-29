package com.mq.rocketmqconsumer.mapper;

import com.mq.rocketmqconsumer.domain.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description: 模拟rocketmq的事务消息的服务接口例子
 * @program: rocketmqconsumer
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-29
 */
@Mapper
@Repository
public interface OrderMapper {
    @Insert("insert into orders (code, name, userid) values (#{code}, #{name}, #{userId})")
    public Integer insertOneOrder(Orders orders);
}
