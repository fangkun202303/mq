package com.mq.rocketmqconsumer.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 模拟rocketmq事务消息的Orders类
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-29
 */
@Data
public class Orders implements Serializable {

    public Integer id;

    public String code;

    public String name;

    public Integer userId;

}
