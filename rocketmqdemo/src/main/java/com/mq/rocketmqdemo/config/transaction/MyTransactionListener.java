package com.mq.rocketmqdemo.config.transaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mq.rocketmqdemo.domain.User;
import com.mq.rocketmqdemo.service.UserService;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: rocketMq的事务生产者的事务监听类
 * @program: rocketmqdemo
 * @author: fangkun
 * @version： 1.0.0
 * @create: 2019-05-30
 */
@Component
public class MyTransactionListener implements TransactionListener {

    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Autowired
    private UserService userService;

    /**
     * @MethodName executeLocalTransaction
     * @Description 执行本地事务
     * @param msg
     * @param arg
     * @Return org.apache.rocketmq.client.producer.LocalTransactionState
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/6/9 13:40
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        // 执行之前先将本地事物的状态标清楚
        localTrans.put(msg.getTransactionId(),0);
        // 向数据库插入一条数据
        JSONObject o = JSON.parseObject(new String(msg.getBody()));
        User user=new User();
        user.id=66;
        user.name=o.get("USERNAME").toString();
        /**
         * @Description 执行本地事务的结果
         */
        Integer integer = null;
        try{
            integer = userService.insertOneUser(user);
            localTrans.put(msg.getTransactionId(),integer);
        }catch (Exception e){
            System.out.printf("%s%n", e.getLocalizedMessage());
            localTrans.put(msg.getTransactionId(),2);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * @MethodName checkLocalTransaction
     * @Description 消息回查
     * @param msg
     * @Return org.apache.rocketmq.client.producer.LocalTransactionState
     * @Exception
     * @Author FangKun
     * @Version V1.0.0
     * @Date 2019/6/9 13:56
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        Integer status = localTrans.get(msg.getTransactionId());
        if(status!=null){
            switch (status){
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                default:
                    return LocalTransactionState.UNKNOW;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
