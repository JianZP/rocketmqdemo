package com.zj.study.mq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 */
public class AsyncProducer {
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
            Message mesg=new Message("base","Tag2",("Hello World"+i).getBytes());
            //        5.发送异步消息
            producer.send(mesg, new SendCallback() {
                /**
                 * 发送成功
                 * @param sendResult
                 */
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送结果："+sendResult);
                }

                /**
                 * 发送失败
                 * @param throwable
                 */
                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送异常："+throwable);
                }
            });
            //发送状态
//            SendStatus status = result.getSendStatus();
            //消息ID
//            String msgId = result.getMsgId();
            //消息接受队列的ID
//            int queueId = result.getMessageQueue().getQueueId();
//            System.out.println("发送状态："+result+";");
            TimeUnit.SECONDS.sleep(1);
        }
//        6.关闭生产者producer
        producer.shutdown();
    }
}
