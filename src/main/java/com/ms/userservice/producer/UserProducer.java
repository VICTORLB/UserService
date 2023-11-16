package com.ms.userservice.producer;

import com.ms.userservice.model.UserEntity;
import com.ms.userservice.dtos.EmailDto;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routineKey;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessageEmail(UserEntity userEntity){
        var emailDto = new EmailDto();
        emailDto.setUserId(userEntity.getUserId());
        emailDto.setEmailTo(userEntity.getEmail());
        emailDto.setSubject("New account created!");
        emailDto.setText(userEntity.getName() + ", welcome to Tarmac team!");

        rabbitTemplate.convertAndSend("", routineKey, emailDto, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });

    }

}
