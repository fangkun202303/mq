package com.mq.rocketmqconsumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: consumer的属性配置类
 * @program: rocketmqconsumer
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq.consumer",ignoreUnknownFields=true)
public class ConsumerProperties {

    public String nameServerAddr;

    public String group;

    public String topic;
}
