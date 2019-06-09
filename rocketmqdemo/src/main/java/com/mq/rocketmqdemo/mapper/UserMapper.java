package com.mq.rocketmqdemo.mapper;

import com.mq.rocketmqdemo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description: 模拟rocketmq的事务消息的服务接口例子
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-29
 */
@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into user (id,name) values (#{id},#{name})")
    public Integer insertOneUser(User user);
}
