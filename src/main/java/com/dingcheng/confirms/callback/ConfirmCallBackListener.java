package com.dingcheng.confirms.callback;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements ConfirmCallback{

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (!ack) {
		  System.out.println("send message failed --> correlationData:"+correlationData+", ack:"+ack+", cause:"+cause);
      }
	}
}
