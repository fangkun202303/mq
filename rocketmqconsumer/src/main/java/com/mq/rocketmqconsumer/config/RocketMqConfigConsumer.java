package com.mq.rocketmqconsumer.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @description: 消费者的配置类
 * @program: rocketmqconsumer
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-28
 */
@Configuration
public abstract class RocketMqConfigConsumer {

    private Logger logger = LoggerFactory.getLogger(RocketMqConfigConsumer.class);

    @Autowired
    private ConsumerProperties consumerProperties;

    /**
     * Description: 开启监听服务
     *
     * @author fangkun
     * @param topic 主题
     * @param tag 标签,这个主题下的消息按标签分类
     * @return void
     * @createDate 2019/5/28 15:58
     **/
    public void msgListener(String topic, String tag) throws MQClientException {
        logger.info("开启" + topic + ":" + tag + "消费者-------------------");
        logger.info("创建消费者了");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerProperties.group);
        logger.info("设置名称服务期地址");
        consumer.setNamesrvAddr(consumerProperties.nameServerAddr);
        logger.info("根据主题,tag订阅消息");
        // 默认的消费顺序是 CONSUME_FROM_LAST_OFFSET
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /**
         * version 1.0 普通发
         */
//        // 这是简单的订阅,没有顺序之说
//        consumer.subscribe(topic,tag);
//        // 开启内部类实现监听
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                return RocketMqConfigConsumer.this.bodyhadler(msgs);
//            }
//        });

        /**
         * version 2.0 顺序发
         */
        //有顺序的订阅部分
        //消费的顺序
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
//        // 只订阅这些分类的消息
//        consumer.subscribe(topic,"*");
//        consumer.registerMessageListener(new MessageListenerOrderly(){
//
//            @Override
//            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
//                return RocketMqConfigConsumer.this.bodyhandlerOfOrder(msgs);
//            }
//        });

        /**
         * version 3.0 延时收
         */
//        consumer.subscribe(topic,"*");
//        // 开启内部类实现监听
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                return RocketMqConfigConsumer.this.bodyhadler(msgs);
//            }
//        });
        /**
         * version 4.0 批量收
         */
//        consumer.setConsumeThreadMax(1);
//        consumer.setConsumeThreadMin(1);
//        // 一次消费两条消息
//        consumer.setConsumeMessageBatchMaxSize(2); // 默认是1
//        consumer.subscribe(topic,"*");
//        // 开启内部类实现监听
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                return RocketMqConfigConsumer.this.bodyhadler(msgs);
//            }
//        });
        /**
         * version 5.0 过滤,最简单的过滤就是 consumer.subscribe(topic,tag); 这就是过滤,只收这个主题下,这个标识的消息,代码就不实现了
         */
        // ============================================
        // ======        分隔符        =================
        // ============================================
        /**
         * version 5.0 过滤,最简单的过滤就是 consumer.subscribe(topic,tag); 这就是过滤,只收这个主题下,这个标识的消息,代码就不实现了
         */

        logger.info("消费者上线了");
        consumer.start();
    }

    /**
     * Description: 消息的业务处理
     *
     * @author fangkun
     * @param msgs
     * @return org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus
     * @createDate 2019/5/28 16:01
     **/
    public abstract ConsumeConcurrentlyStatus bodyhadler(List<MessageExt> msgs);

    /**
     * Description: 顺序的处理消息
     *
     * @author fangkun
     * @param msgs 消息集合
     * @return org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus
     * @createDate 2019/5/29 8:48
     **/
    public abstract ConsumeOrderlyStatus bodyhandlerOfOrder(List<MessageExt> msgs);
}
