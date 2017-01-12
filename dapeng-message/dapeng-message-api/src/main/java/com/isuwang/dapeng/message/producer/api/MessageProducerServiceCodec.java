package com.isuwang.dapeng.message.producer.api;

import com.isuwang.dapeng.core.*;
import com.isuwang.org.apache.thrift.TException;
import com.isuwang.org.apache.thrift.protocol.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MessageProducerServiceCodec {


	public static class sendMessage_args {

		private String topic;

		public String getTopic() {
			return this.topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		private String message;

		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {
			this.message = message;
		}


		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder("{");

			stringBuilder.append("\"").append("topic").append("\":\"").append(topic).append("\",");

			stringBuilder.append("\"").append("message").append("\":\"").append(message).append("\",");

			if (stringBuilder.lastIndexOf(",") > 0)
				stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
			stringBuilder.append("}");

			return stringBuilder.toString();
		}

	}


	public static class sendMessage_result {


		@Override
		public String toString() {
			return "{}";
		}

	}

	public static class SendMessage_argsSerializer implements TBeanSerializer<sendMessage_args> {

		@Override
		public void read(sendMessage_args bean, TProtocol iprot) throws TException {

			TField schemeField;
			iprot.readStructBegin();

			while (true) {
				schemeField = iprot.readFieldBegin();
				if (schemeField.type == TType.STOP) {
					break;
				}

				switch (schemeField.id) {

					case 1:
						if (schemeField.type == TType.STRING) {
							String elem0 = iprot.readString();
							bean.setTopic(elem0);
						} else {
							TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;

					case 2:
						if (schemeField.type == TType.STRING) {
							String elem0 = iprot.readString();
							bean.setMessage(elem0);
						} else {
							TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;


					default:
						TProtocolUtil.skip(iprot, schemeField.type);

				}
				iprot.readFieldEnd();
			}
			iprot.readStructEnd();

			validate(bean);
		}

		@Override
		public void write(sendMessage_args bean, TProtocol oprot) throws TException {

			validate(bean);
			oprot.writeStructBegin(new TStruct("sendMessage_args"));


			oprot.writeFieldBegin(new TField("topic", TType.STRING, (short) 1));
			String elem0 = bean.getTopic();
			oprot.writeString(elem0);

			oprot.writeFieldEnd();

			oprot.writeFieldBegin(new TField("message", TType.STRING, (short) 2));
			String elem1 = bean.getMessage();
			oprot.writeString(elem1);

			oprot.writeFieldEnd();

			oprot.writeFieldStop();
			oprot.writeStructEnd();
		}

		public void validate(sendMessage_args bean) throws TException {

			if (bean.getTopic() == null)
				throw new SoaException(SoaBaseCode.NotNull, "topic字段不允许为空");

			if (bean.getMessage() == null)
				throw new SoaException(SoaBaseCode.NotNull, "message字段不允许为空");

		}


		@Override
		public String toString(sendMessage_args bean) {
			return bean == null ? "null" : bean.toString();
		}

	}

	public static class SendMessage_resultSerializer implements TBeanSerializer<sendMessage_result> {
		@Override
		public void read(sendMessage_result bean, TProtocol iprot) throws TException {

			TField schemeField;
			iprot.readStructBegin();

			while (true) {
				schemeField = iprot.readFieldBegin();
				if (schemeField.type == TType.STOP) {
					break;
				}

				switch (schemeField.id) {
					case 0:  //SUCCESS
						if (schemeField.type == TType.VOID) {

							TProtocolUtil.skip(iprot, schemeField.type);
						} else {
							TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;
				  /*
                  case 1: //ERROR
                  bean.setSoaException(new SoaException());
                  new SoaExceptionSerializer().read(bean.getSoaException(), iprot);
                  break A;
                  */
					default:
						TProtocolUtil.skip(iprot, schemeField.type);
				}
				iprot.readFieldEnd();
			}
			iprot.readStructEnd();

			validate(bean);
		}

		@Override
		public void write(sendMessage_result bean, TProtocol oprot) throws TException {

			validate(bean);
			oprot.writeStructBegin(new TStruct("sendMessage_result"));


			oprot.writeFieldStop();
			oprot.writeStructEnd();
		}


		public void validate(sendMessage_result bean) throws TException {

		}


		@Override
		public String toString(sendMessage_result bean) {
			return bean == null ? "null" : bean.toString();
		}
	}

	public static class sendMessage<I extends com.isuwang.dapeng.message.producer.api.service.MessageProducerService> extends SoaProcessFunction<I, sendMessage_args, sendMessage_result, SendMessage_argsSerializer, SendMessage_resultSerializer> {
		public sendMessage() {
			super("sendMessage", new SendMessage_argsSerializer(), new SendMessage_resultSerializer());
		}

		@Override
		public sendMessage_result getResult(I iface, sendMessage_args args) throws TException {
			sendMessage_result result = new sendMessage_result();

			iface.sendMessage(args.topic, args.message);

			return result;
		}


		@Override
		public sendMessage_args getEmptyArgsInstance() {
			return new sendMessage_args();
		}

		@Override
		protected boolean isOneway() {
			return false;
		}
	}

	public static class sendMessages_args {

		private String topic;

		public String getTopic() {
			return this.topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		private java.util.List<String> messages;

		public java.util.List<String> getMessages() {
			return this.messages;
		}

		public void setMessages(java.util.List<String> messages) {
			this.messages = messages;
		}


		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder("{");

			stringBuilder.append("\"").append("topic").append("\":\"").append(topic).append("\",");

			stringBuilder.append("\"").append("messages").append("\":").append(messages).append(",");

			if (stringBuilder.lastIndexOf(",") > 0)
				stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
			stringBuilder.append("}");

			return stringBuilder.toString();
		}

	}


	public static class sendMessages_result {


		@Override
		public String toString() {
			return "{}";
		}

	}

	public static class SendMessages_argsSerializer implements TBeanSerializer<sendMessages_args> {

		@Override
		public void read(sendMessages_args bean, TProtocol iprot) throws TException {

			TField schemeField;
			iprot.readStructBegin();

			while (true) {
				schemeField = iprot.readFieldBegin();
				if (schemeField.type == TType.STOP) {
					break;
				}

				switch (schemeField.id) {

					case 1:
						if (schemeField.type == TType.STRING) {
							String elem0 = iprot.readString();
							bean.setTopic(elem0);
						} else {
							TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;

					case 2:
						if (schemeField.type == TType.LIST) {
							TList _list0 = iprot.readListBegin();
							java.util.List<String> elem0 = new java.util.ArrayList<>(_list0.size);
							for (int _i0 = 0; _i0 < _list0.size; ++_i0) {
								String elem1 = iprot.readString();
								elem0.add(elem1);
							}
							iprot.readListEnd();
							bean.setMessages(elem0);
						} else {
							TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;


					default:
						TProtocolUtil.skip(iprot, schemeField.type);

				}
				iprot.readFieldEnd();
			}
			iprot.readStructEnd();

			validate(bean);
		}

		@Override
		public void write(sendMessages_args bean, TProtocol oprot) throws TException {

			validate(bean);
			oprot.writeStructBegin(new TStruct("sendMessages_args"));


			oprot.writeFieldBegin(new TField("topic", TType.STRING, (short) 1));
			String elem0 = bean.getTopic();
			oprot.writeString(elem0);

			oprot.writeFieldEnd();

			oprot.writeFieldBegin(new TField("messages", TType.LIST, (short) 2));
			java.util.List<String> elem1 = bean.getMessages();

			oprot.writeListBegin(new TList(TType.STRING, elem1.size()));
			for (String elem2 : elem1) {
				oprot.writeString(elem2);
			}
			oprot.writeListEnd();


			oprot.writeFieldEnd();

			oprot.writeFieldStop();
			oprot.writeStructEnd();
		}

		public void validate(sendMessages_args bean) throws TException {

			if (bean.getTopic() == null)
				throw new SoaException(SoaBaseCode.NotNull, "topic字段不允许为空");

			if (bean.getMessages() == null)
				throw new SoaException(SoaBaseCode.NotNull, "messages字段不允许为空");

		}


		@Override
		public String toString(sendMessages_args bean) {
			return bean == null ? "null" : bean.toString();
		}

	}

	public static class SendMessages_resultSerializer implements TBeanSerializer<sendMessages_result> {
		@Override
		public void read(sendMessages_result bean, TProtocol iprot) throws TException {

			TField schemeField;
			iprot.readStructBegin();

			while (true) {
				schemeField = iprot.readFieldBegin();
				if (schemeField.type == TType.STOP) {
					break;
				}

				switch (schemeField.id) {
					case 0:  //SUCCESS
						if (schemeField.type == TType.VOID) {

							TProtocolUtil.skip(iprot, schemeField.type);
						} else {
							TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;
                  /*
                  case 1: //ERROR
                  bean.setSoaException(new SoaException());
                  new SoaExceptionSerializer().read(bean.getSoaException(), iprot);
                  break A;
                  */
					default:
						TProtocolUtil.skip(iprot, schemeField.type);
				}
				iprot.readFieldEnd();
			}
			iprot.readStructEnd();

			validate(bean);
		}

		@Override
		public void write(sendMessages_result bean, TProtocol oprot) throws TException {

			validate(bean);
			oprot.writeStructBegin(new TStruct("sendMessages_result"));


			oprot.writeFieldStop();
			oprot.writeStructEnd();
		}


		public void validate(sendMessages_result bean) throws TException {

		}


		@Override
		public String toString(sendMessages_result bean) {
			return bean == null ? "null" : bean.toString();
		}
	}

	public static class sendMessages<I extends com.isuwang.dapeng.message.producer.api.service.MessageProducerService> extends SoaProcessFunction<I, sendMessages_args, sendMessages_result, SendMessages_argsSerializer, SendMessages_resultSerializer> {
		public sendMessages() {
			super("sendMessages", new SendMessages_argsSerializer(), new SendMessages_resultSerializer());
		}

		@Override
		public sendMessages_result getResult(I iface, sendMessages_args args) throws TException {
			sendMessages_result result = new sendMessages_result();

			iface.sendMessages(args.topic, args.messages);

			return result;
		}


		@Override
		public sendMessages_args getEmptyArgsInstance() {
			return new sendMessages_args();
		}

		@Override
		protected boolean isOneway() {
			return false;
		}
	}


	public static class getServiceMetadata_args {

		@Override
		public String toString() {
			return "{}";
		}
	}


	public static class getServiceMetadata_result {

		private String success;

		public String getSuccess() {
			return success;
		}

		public void setSuccess(String success) {
			this.success = success;
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder("{");
			stringBuilder.append("\"").append("success").append("\":\"").append(this.success).append("\",");
			stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
			stringBuilder.append("}");

			return stringBuilder.toString();
		}
	}

	public static class GetServiceMetadata_argsSerializer implements TBeanSerializer<getServiceMetadata_args> {

		@Override
		public void read(getServiceMetadata_args bean, TProtocol iprot) throws TException {

			TField schemeField;
			iprot.readStructBegin();

			while (true) {
				schemeField = iprot.readFieldBegin();
				if (schemeField.type == TType.STOP) {
					break;
				}
				switch (schemeField.id) {
					default:
						TProtocolUtil.skip(iprot, schemeField.type);

				}
				iprot.readFieldEnd();
			}
			iprot.readStructEnd();

			validate(bean);
		}


		@Override
		public void write(getServiceMetadata_args bean, TProtocol oprot) throws TException {

			validate(bean);
			oprot.writeStructBegin(new TStruct("getServiceMetadata_args"));
			oprot.writeFieldStop();
			oprot.writeStructEnd();
		}

		public void validate(getServiceMetadata_args bean) throws TException {
		}

		@Override
		public String toString(getServiceMetadata_args bean) {
			return bean == null ? "null" : bean.toString();
		}

	}

	public static class GetServiceMetadata_resultSerializer implements TBeanSerializer<getServiceMetadata_result> {
		@Override
		public void read(getServiceMetadata_result bean, TProtocol iprot) throws TException {

			TField schemeField;
			iprot.readStructBegin();

			while (true) {
				schemeField = iprot.readFieldBegin();
				if (schemeField.type == TType.STOP) {
					break;
				}

				switch (schemeField.id) {
					case 0:  //SUCCESS
						if (schemeField.type == TType.STRING) {
							bean.setSuccess(iprot.readString());
						} else {
							TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;
					default:
						TProtocolUtil.skip(iprot, schemeField.type);
				}
				iprot.readFieldEnd();
			}
			iprot.readStructEnd();

			validate(bean);
		}

		@Override
		public void write(getServiceMetadata_result bean, TProtocol oprot) throws TException {

			validate(bean);
			oprot.writeStructBegin(new TStruct("getServiceMetadata_result"));

			oprot.writeFieldBegin(new TField("success", TType.STRING, (short) 0));
			oprot.writeString(bean.getSuccess());
			oprot.writeFieldEnd();

			oprot.writeFieldStop();
			oprot.writeStructEnd();
		}

		public void validate(getServiceMetadata_result bean) throws TException {

			if (bean.getSuccess() == null)
				throw new SoaException(SoaBaseCode.NotNull, "success字段不允许为空");
		}

		@Override
		public String toString(getServiceMetadata_result bean) {
			return bean == null ? "null" : bean.toString();
		}
	}

	public static class getServiceMetadata<I extends com.isuwang.dapeng.message.producer.api.service.MessageProducerService> extends SoaProcessFunction<I, getServiceMetadata_args, getServiceMetadata_result, GetServiceMetadata_argsSerializer, GetServiceMetadata_resultSerializer> {
		public getServiceMetadata() {
			super("getServiceMetadata", new GetServiceMetadata_argsSerializer(), new GetServiceMetadata_resultSerializer());
		}

		@Override
		public getServiceMetadata_result getResult(I iface, getServiceMetadata_args args) throws TException {
			getServiceMetadata_result result = new getServiceMetadata_result();

			try (InputStreamReader isr = new InputStreamReader(MessageProducerServiceCodec.class.getClassLoader().getResourceAsStream("com.isuwang.dapeng.message.producer.api.service.MessageProducerService.xml"));
				 BufferedReader in = new BufferedReader(isr)) {
				int len = 0;
				StringBuilder str = new StringBuilder("");
				String line;
				while ((line = in.readLine()) != null) {

					if (len != 0) {
						str.append("\r\n").append(line);
					} else {
						str.append(line);
					}
					len++;
				}
				result.success = str.toString();

			} catch (Exception e) {
				e.printStackTrace();
				result.success = "";
			}

			return result;
		}

		@Override
		public getServiceMetadata_args getEmptyArgsInstance() {
			return new getServiceMetadata_args();
		}

		@Override
		protected boolean isOneway() {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public static class Processor<I extends com.isuwang.dapeng.message.producer.api.service.MessageProducerService> extends SoaBaseProcessor {
		public Processor(I iface) {
			super(iface, getProcessMap(new java.util.HashMap<>()));
		}

		@SuppressWarnings("unchecked")
		private static <I extends com.isuwang.dapeng.message.producer.api.service.MessageProducerService> java.util.Map<String, SoaProcessFunction<I, ?, ?, ? extends TBeanSerializer<?>, ? extends TBeanSerializer<?>>> getProcessMap(java.util.Map<String, SoaProcessFunction<I, ?, ?, ? extends TBeanSerializer<?>, ? extends TBeanSerializer<?>>> processMap) {

			processMap.put("sendMessage", new sendMessage());

			processMap.put("sendMessages", new sendMessages());

			processMap.put("getServiceMetadata", new getServiceMetadata());

			return processMap;
		}
	}

}
      