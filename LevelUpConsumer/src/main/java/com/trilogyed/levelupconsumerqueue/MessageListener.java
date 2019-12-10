package com.trilogyed.levelupconsumerqueue;

import com.trilogyed.levelupconsumerqueue.util.messages.LevelUpEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service

public class MessageListener {

    @RabbitListener(queues = LevelUpQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessages(LevelUpEntry levelUp) {
        System.out.println(levelUp.toString());
    }
}
