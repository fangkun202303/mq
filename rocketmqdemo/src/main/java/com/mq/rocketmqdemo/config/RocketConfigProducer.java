package com.mq.rocketmqdemo.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 消息生产者的配置类
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-28
 */
@Configuration
public class RocketConfigProducer {

    private Logger logger = LoggerFactory.getLogger(RocketConfigProducer.class);

    @Autowired
    private ProducerProperties producerProperties;


    /**
     * Description: 普通的是消息发送
     * producer类的创建类，需要注意的是这个producer一个程序里面只能出现一个，当重复创建时就会报错
     *
     * @author fangkun
     * @param
     * @return org.apache.rocketmq.client.producer.DefaultMQProducer
     * @createDate 2019/5/28 14:30
     **/
//    @Bean
//    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "default", havingValue = "true")
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        logger.info("加载这个配置了========.>>>>>>>");
        logger.info("创建生产者了");
        DefaultMQProducer producer = new DefaultMQProducer(producerProperties.groupName);
        logger.info("生产者设置名称服务地址了");
        producer.setNamesrvAddr(producerProperties.nameServerAddr);
        logger.info("生产者是否设置VIP通道");
        producer.setVipChannelEnabled(false);
        logger.info("消息发送失败是重复发送的次数");
        producer.setRetryTimesWhenSendAsyncFailed(4);
        logger.info("生产者启动,建起长连接");
        producer.start();
        return producer;
    }
}
