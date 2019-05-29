package com.mq.rocketmqconsumer.config;

import com.mq.rocketmqconsumer.service.Msghandler;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @description: 根据不同主题自定义一个监听
 * @program: rocketmqconsumer
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-28
 */
@Configuration
public class MyRocketMqConfigConsumer extends RocketMqConfigConsumer implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(MyRocketMqConfigConsumer.class);

    @Autowired
    private Msghandler msghandler;

    /**
     * Description: 根据监听的消息具体处理
     *
     * @author fangkun
     * @param msgs
     * @return org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus
     * @createDate 2019/5/28 16:06
     **/
    @Override
    public ConsumeConcurrentlyStatus bodyhadler(List<MessageExt> msgs) {
        logger.info("收到消息的时间是: "+new Date().toString()+"开始处理消息了!!!! ");
        ConsumeConcurrentlyStatus consumeConcurrentlyStatus=null;
        logger.info("这个消息集合的长度是: "+msgs.size());
        for (MessageExt msg : msgs){
            try {
                logger.info("这个消息的消息ID是: "+msg.getMsgId());
                if(new String(msg.getBody(),"utf-8").equals("子夜笙歌落第二个失败测试")){
                    logger.info("这个消息是: "+new String(msg.getBody(),"utf-8"));
                    throw new RuntimeException();
                }
                msghandler.msgHandler("这个消息是: "+new String(msg.getBody(),"utf-8"));
            } catch (UnsupportedEncodingException ex) {
                logger.info("消息编码出现异常!!!! 向合格消息重发");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        try {
            logger.info("睡两秒!!!");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 返回消息处理的状态,
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @Override
    public ConsumeOrderlyStatus bodyhandlerOfOrder(List<MessageExt> msgs) {
        logger.info("开始处理顺序消息了!!!! ");
        ConsumeOrderlyStatus consumeOrderlyStatus=null;
        logger.info("这个消息集合的长度是: "+msgs.size());
        for (MessageExt msg : msgs){
            try {
                logger.info("这个消息的消息ID是: "+msg.getMsgId()+"这个消息的对列是: "+msg.getQueueId());
//                if(new String(msg.getBody(),"utf-8").equals("子夜笙歌落第二个失败测试")){
//                    logger.info("这个消息是: "+new String(msg.getBody(),"utf-8"));
//                    throw new RuntimeException();
//                }
                msghandler.msgHandler("这个消息是: "+new String(msg.getBody(),"utf-8"));
            } catch (UnsupportedEncodingException ex) {
                logger.info("消息编码出现异常!!!! 向合格消息重发");
                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }
        }

        // 返回消息处理的状态,
        return ConsumeOrderlyStatus.SUCCESS;
    }

    /**
     * Description: 设置监听什么主题类消息
     *
     * @author fangkun
     * @param contextRefreshedEvent
     * @return void
     * @createDate 2019/5/28 16:07
     **/
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            super.msgListener("demo","fenzu");
        } catch (MQClientException e) {
            logger.info("监听失败! 失败原因: "+ e.getErrorMessage());
        }
    }
}
