package com.mq.rocketmqdemo.controller;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.alibaba.fastjson.JSON.*;

/**
 * @description: RocketMQ的测试门面controller
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-28
 */
@RestController
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    public DefaultMQProducer defaultMQProducer;

    @SuppressWarnings("all")
    @GetMapping("/testmesg")
    public String sendMsg(@RequestParam String msg){
        Object m=null;
        SendResult result=null;
        try {
            /**
             * version 1.0 普通发
             */
        // 这是简单的消息收发
//            defaultMQProducer.send(new Message("demo","fenzu","yonghuID",msg.getBytes()) , new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    logger.info(sendResult.getMsgId()+"消息发送成功! ");
//                    s[0]=toJSONString(sendResult);
//                    /**
//                     * sendResult的数据参数
//                     *{
//                     * 	"messageQueue": {
//                     * 		"brokerName": "CC-20",
//                     * 		"queueId": 0,
//                     * 		"topic": "demo"
//                     *        },
//                     * 	"msgId": "A9FED505518818B4AAC28ECC14800003",
//                     * 	"offsetMsgId": "A9FED50500002A9F0000000000002907",
//                     * 	"queueOffset": 1,
//                     * 	"regionId": "DefaultRegion",
//                     * 	"sendStatus": "SEND_OK",
//                     * 	"traceOn": true
//                     * }
//                     **/
//                    logger.info("这个结果是: "+ toJSONString(sendResult));
//                }
//
//                @Override
//                public void onException(Throwable e) {
//                    logger.info("消息发送失败! 原因是: "+e.getMessage());
//                }
//            });

            // 模拟消息接收失败的情况
//            defaultMQProducer.send(new Message("demo","fenzu","yonghuID",(msg+"第二个失败测试").getBytes()) , new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    logger.info(sendResult.getMsgId()+"消息发送成功! ");
//                    s[0]=toJSONString(sendResult);
//                    logger.info("这个结果是: "+ toJSONString(sendResult));
//                }
//
//                @Override
//                public void onException(Throwable e) {
//                    logger.info("消息发送失败! 原因是: "+e.getMessage());
//                }
//            });

            /**
             * version 2.0 顺序发
             */
            // 有顺序的发消息
            // 手动创建
//            String[] tags = new String[]{"创建订单", "支付", "发货", "收货", "五星好评"};
//            for(int x=0; x<20;x++){
//                SendResult sendResult = defaultMQProducer.send(
//                        /**
//                         * topic 消息主题
//                         * tag: 消息表示,就是消息的二级分类
//                         * key: 就是让消费者有顺序的消费,举例: 订单id,这个订单下的 "创建订单", "支付", "发货", "收货", "五星好评"
//                         *            有顺序的处理
//                         * message:消息
//                         **/
//                        new Message("demo", tags[x % tags.length], "yonghuID"+x/5, (msg+x+tags[x % tags.length]).getBytes()),
//                        new MessageQueueSelector() {
//                            @Override
//                            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                                Integer id = (Integer) arg;
//                                int index = id % mqs.size();
//                                System.out.println(toJSONString(msg));
//                                return mqs.get(index);
//                            }
//                        }, x/5);
//                System.out.printf("%s%n", sendResult);
//                if(sendResult==null){
//                    // 说明发送失败
//                    throw new RuntimeException("消息没有发送成功!");
//                }
//            }

            /**
             * version 3.0 延时发
             */
//            StopWatch stopWatch = new StopWatch();
//            stopWatch.start();
//            Message message = new Message("demo", "demotag", "yonghuID" , msg.getBytes());
//            // 设置时间延时等级,就是在/conf/broker.conf文件中添加的配置
//            message.setDelayTimeLevel(3);
//
//            logger.info("======>>>>>>>>这个消息的结构是: "+toJSONString(message));
//            defaultMQProducer.send(message, new MessageQueueSelector() {
//                @Override
//                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                    return mqs.get(0);
//                }
//            }, 1, new SendCallback(){
//
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    logger.info(sendResult.getMsgId()+"消息发送成功! ");
//                    logger.info("这个结果是: "+ toJSONString(sendResult));
//                }
//
//                @Override
//                public void onException(Throwable e) {
//                    logger.info("消息发送失败! 原因是: "+e.getMessage());
//                }
//            });
//
//            stopWatch.stop();
//            System.out.println("成功发送这条消息的时间是: "+new Date().toString() + " 发送1条延迟消息耗时:" + stopWatch.getTotalTimeMillis());
//            m=message;
            /**
             * version 4.0 批量发
             */
//            List<Message> list=new ArrayList<>();
//            for (int x=0;x<10;x++){
//                list.add(new Message("demo", "demotag", "yonghuID_"+(x+1) , msg.getBytes()));
//            }
//            SendResult sendResult = defaultMQProducer.send(list);
//            if(sendResult==null){
//                // 出事了,没有发送成功!
//                throw new RuntimeException();
//            }
//            m=list;
            /**
             * version 5.0 消息过滤,提供者这边没有限制,消费者是有限制的
             */
            // ============================================
            // ======        分隔符        =================
            // ============================================
            /**
             * version 6.0 消息事务
             */
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toJSONString(m);
    }

}
