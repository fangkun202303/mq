package com.mq.rocketmqdemo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 模拟rocketmq事务消息的user类
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-29
 */
@Data
public class User implements Serializable {

    public Integer id;

    public String name;
}
