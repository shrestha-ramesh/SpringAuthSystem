package com.user.config;

import com.user.dto.UserLogInDTO;
import com.user.rabbitmq.MQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@EnableScheduling
public class Scheduler {

    @Autowired
    RabbitTemplate template;

    @Scheduled(fixedDelay = 1000)
    public void scheduler() throws InterruptedException{
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime =  current.format(formatter);
        Thread.sleep(1000);
        UserLogInDTO userLogInDTO = new UserLogInDTO();
        userLogInDTO.setEmailAddress("shresthashare@gmail.com");
        userLogInDTO.setToken("faketoken");
        userLogInDTO.setEmailVerify(true);
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, userLogInDTO);
    }
}
