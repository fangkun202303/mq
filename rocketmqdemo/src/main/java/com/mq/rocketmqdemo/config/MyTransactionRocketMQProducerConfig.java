package com.mq.rocketmqdemo.config;

import com.mq.rocketmqdemo.config.transaction.MyTransactionListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @description: rocketmq的事务生产者的配置类
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-29
 */
@Configuration
public class MyTransactionRocketMQProducerConfig {

    private Logger logger = LoggerFactory.getLogger(RocketConfigProducer.class);

    @Autowired
    private ProducerProperties producerProperties;

    @Autowired
    private MyTransactionListener myTransactionListener;


    /**
     * Description: 普通的是消息发送
     * producer类的创建类，需要注意的是这个producer一个程序里面只能出现一个，当重复创建时就会报错
     *
     * @author fangkun
     * @param
     * @return org.apache.rocketmq.client.producer.DefaultMQProducer
     * @createDate 2019/5/28 14:30
     **/
    @Bean
    public TransactionMQProducer transactionMQProducer() throws MQClientException {
        logger.info("加载Transaction这个配置了========.>>>>>>>");
        logger.info("创建Transaction生产者了");
        TransactionMQProducer producer=new TransactionMQProducer(producerProperties.groupName);
        /**
         * @Description 4.5.1 就没有这种设置了，翻看源码可得知
         *     private int checkThreadPoolMinSize = 1;
         *     private int checkThreadPoolMaxSize = 1;
         *     private int checkRequestHoldMax = 2000;
         *    源码有设置默认的，用的是单线程，保证没有并发的干扰
         */
//        // 事务回查最小并发数
//        producer.setCheckThreadPoolMinSize(2);
//        // 事务回查最大并发数
//        producer.setCheckThreadPoolMaxSize(2);
//        // 队列数
//        producer.setCheckRequestHoldMax(2000);
        // 设置执行的连接池
        ExecutorService executorService = new ThreadPoolExecutor(2,
                5,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        logger.info("Transaction生产者设置名称服务地址了");
        producer.setNamesrvAddr(producerProperties.nameServerAddr);
        logger.info("Transaction生产者是否设置VIP通道");
        producer.setVipChannelEnabled(false);
        logger.info("消息发送失败是重复发送的次数");
        producer.setRetryTimesWhenSendAsyncFailed(4);
        //设置监听器
        producer.setTransactionListener(myTransactionListener);
        logger.info("Transaction生产者启动,建起长连接");
        producer.start();
        return producer;
    }
}
