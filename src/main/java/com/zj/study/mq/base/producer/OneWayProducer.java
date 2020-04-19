package com.zj.study.mq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception {
//        1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer=new DefaultMQProducer("groupone");
//        2.指定Nameserver地址
        producer.setNamesrvAddr("192.168.192.130:9876;192.168.192.131:9876");
//        3.启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            //        4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息的主题Topic
             * 参数二：消息的Tag
             * 参数三：消息的数据内容
             */
            Message mesg=new Message("base","Tag3",("Hello World，单向消息"+i).getBytes());
            //        5.发送单向消息
            producer.sendOneway(mesg);
/*            //发送状态
            SendStatus status = result.getSendStatus();
            //消息ID
            String msgId = result.getMsgId();
            //消息接受队列的ID
            int queueId = result.getMessageQueue().getQueueId();*/
//            System.out.println("发送状态："+result+";");
            TimeUnit.SECONDS.sleep(1);
        }
//        6.关闭生产者producer
        producer.shutdown();
    }
}
