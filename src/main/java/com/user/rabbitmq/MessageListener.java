package com.user.rabbitmq;

import com.user.model.user.UserRegister;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(UserRegister userRegister){
        System.out.println(userRegister);
    }
}
