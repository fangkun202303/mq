package com.mq.rocketmqdemo.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 消息生产者的Properties 类
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq.producer",ignoreUnknownFields=true)
public class ProducerProperties {

    // 名称服务的地址
    public String nameServerAddr;

    //分组地址
    public String groupName;

    // 消息的主题
    public String topic;
}
