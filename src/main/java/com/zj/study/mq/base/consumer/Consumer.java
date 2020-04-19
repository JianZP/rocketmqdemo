package com.zj.study.mq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 消息的接收者
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
//        1.创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("groupone");
//        2.指定Nameserver地址
        consumer.setNamesrvAddr("192.168.192.130:9876;192.168.192.131:9876");
//        3.订阅主题Topic和Tag
        consumer.subscribe("base","Tag1 || Tag2");
        //设定消费模式：负载均衡|广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
//        4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //接收消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt:list) {
                    System.out.println(new String( messageExt.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
//        5.启动消费者consumer
        consumer.start();
    }
}
