package com.isuwang.dapeng.message.producer.api;

import com.isuwang.dapeng.core.SoaException;
import com.isuwang.dapeng.message.producer.api.MessageProducerServiceCodec.*;
import com.isuwang.dapeng.remoting.BaseServiceClient;
import com.isuwang.org.apache.thrift.TException;

public class MessageProducerServiceClient extends BaseServiceClient {

	public MessageProducerServiceClient() {
		super("com.isuwang.dapeng.message.producer.api.service.MessageProducerService", "1.0.0");
	}

	@Override
	protected boolean isSoaTransactionalProcess() {
		return false;
	}

	/**
	 * 指定Topic，发送消息
	 **/
	public void sendMessage(String topic, String message) throws SoaException {
		initContext("sendMessage");

		try {
			sendMessage_args sendMessage_args = new sendMessage_args();
			sendMessage_args.setTopic(topic);
			sendMessage_args.setMessage(message);
			sendMessage_result response = sendBase(sendMessage_args, new sendMessage_result(), new SendMessage_argsSerializer(), new SendMessage_resultSerializer());

		} catch (SoaException e) {
			throw e;
		} catch (TException e) {
			throw new SoaException(e);
		} finally {
			destoryContext();
		}
	}


	/**
	 * 指定Topic，批量发送消息
	 **/

	public void sendMessages(String topic, java.util.List<String> messages) throws SoaException {
		initContext("sendMessages");

		try {
			sendMessages_args sendMessages_args = new sendMessages_args();
			sendMessages_args.setTopic(topic);
			sendMessages_args.setMessages(messages);
			sendMessages_result response = sendBase(sendMessages_args, new sendMessages_result(), new SendMessages_argsSerializer(), new SendMessages_resultSerializer());

		} catch (SoaException e) {
			throw e;
		} catch (TException e) {
			throw new SoaException(e);
		} finally {
			destoryContext();
		}
	}


	/**
	 * getServiceMetadata
	 **/
	public String getServiceMetadata() throws SoaException {
		initContext("getServiceMetadata");
		try {
			getServiceMetadata_args getServiceMetadata_args = new getServiceMetadata_args();
			getServiceMetadata_result response = sendBase(getServiceMetadata_args, new getServiceMetadata_result(), new GetServiceMetadata_argsSerializer(), new GetServiceMetadata_resultSerializer());
			return response.getSuccess();

		} catch (SoaException e) {
			throw e;
		} catch (TException e) {
			throw new SoaException(e);
		} finally {
			destoryContext();
		}
	}

}
