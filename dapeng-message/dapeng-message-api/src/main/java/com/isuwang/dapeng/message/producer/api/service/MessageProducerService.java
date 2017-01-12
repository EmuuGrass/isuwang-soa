
package com.isuwang.dapeng.message.producer.api.service;

import com.isuwang.dapeng.core.Processor;
import com.isuwang.dapeng.core.Service;

/**
 * Dapeng Message Kafka Producer
 **/
@Service(version = "1.0.0")
@Processor(className = "com.isuwang.dapeng.message.producer.api.MessageProducerServiceCodec$Processor")
public interface MessageProducerService {

	/**
	 * 指定Topic，发送消息
	 **/
	void sendMessage(String topic, String message) throws com.isuwang.dapeng.core.SoaException;


	/**
	 * 指定Topic，批量发送消息
	 **/
	void sendMessages(String topic, java.util.List<String> messages) throws com.isuwang.dapeng.core.SoaException;


}
