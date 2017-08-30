package com.dingcheng.confirms.publish;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publishService")
public class PublishService {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void send(String exchange, String routingKey, Object message) {

    rabbitTemplate.convertAndSend(exchange, routingKey, message,
        new CorrelationData(System.currentTimeMillis() + ""));

  }
}
