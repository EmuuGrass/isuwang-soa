package com.isuwang.dapeng.remoting.netty;

import com.isuwang.dapeng.core.*;
import com.isuwang.dapeng.remoting.SoaConnection;
import com.isuwang.org.apache.thrift.TApplicationException;
import com.isuwang.org.apache.thrift.TException;
import com.isuwang.org.apache.thrift.protocol.TMessage;
import com.isuwang.org.apache.thrift.protocol.TMessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Created by tangliu on 17/4/10.
 */
public class SoaLocalConnectionImpl implements SoaConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoaLocalConnectionImpl.class);

    private static TransactionContext generateTransactionContext(InvocationContext invoContext) {

        TransactionContext context = new TransactionContext();
        context.setSeqid(invoContext.getSeqid());
        context.setHeader(invoContext.getHeader());
        context.setCalleeIp(invoContext.getCalleeIp());
        context.setCalleePort(invoContext.getCalleePort());

        return context;
    }


    public <I> ByteBuf callLocalService(ByteBuf requestBuf) throws TException {

        TransactionContext currTransContext = TransactionContext.Factory.getCurrentInstance();   //上一层请求的服务端context
        InvocationContext currInvoContext = InvocationContext.Factory.getCurrentInstance(); //当前请求的客户端context
        TransactionContext newTransContext = generateTransactionContext(currInvoContext);
        InvocationContext.Factory.removeCurrentInstance();
        TransactionContext.Factory.setCurrentInstance(newTransContext);

        TransactionContext context = TransactionContext.Factory.getCurrentInstance();
        SoaHeader soaHeader = context.getHeader();

        SoaBaseProcessor<I> processor = (SoaBaseProcessor<I>) ProcessorCache.getMatchedProcessors(soaHeader.getServiceName(), soaHeader.getVersionName()).get(0);

        String methodName = soaHeader.getMethodName();

        SoaProcessFunction<I, Object, Object, ? extends TBeanSerializer<Object>, ? extends TBeanSerializer<Object>> soaProcessFunction = (SoaProcessFunction<I, Object, Object, ? extends TBeanSerializer<Object>, ? extends TBeanSerializer<Object>>) processor.getProcessMapView().get(methodName);
        if (soaProcessFunction == null)
            throw new SoaException("系统错误", "方法(" + methodName + ")不存在");

        TSoaTransport inputSoaTransport = new TSoaTransport(requestBuf);
        TSoaServiceProtocol inputProtocol = new TSoaServiceProtocol(inputSoaTransport, false);

        Object args = soaProcessFunction.getEmptyArgsInstance();
        soaProcessFunction.getReqSerializer().read(args, inputProtocol);
        inputProtocol.readMessageEnd();


        Object result = null;
        result = soaProcessFunction.getResult(processor.getIface(), args);

        ByteBuf outputBuf = Unpooled.directBuffer(8192);
        TSoaTransport outputSoaTransport = new TSoaTransport(outputBuf);
        TSoaServiceProtocol outputProtocol = new TSoaServiceProtocol(outputSoaTransport, false);


        soaHeader.setRespCode(Optional.of("0000"));
        soaHeader.setRespMessage(Optional.of("成功"));
        outputProtocol.writeMessageBegin(new TMessage(context.getHeader().getMethodName(), TMessageType.CALL, context.getSeqid()));
        soaProcessFunction.getResSerializer().write(result, outputProtocol);
        outputProtocol.writeMessageEnd();

        InvocationContext.Factory.setCurrentInstance(currInvoContext);
        TransactionContext.Factory.setCurrentInstance(currTransContext);

        //释放资源
        inputSoaTransport.close();
        outputSoaTransport.close();
        return outputBuf;
    }


    public <REQ, RESP> RESP send(REQ request, RESP response, TBeanSerializer<REQ> requestSerializer, TBeanSerializer<RESP> responseSerializer) throws TException {
        InvocationContext context = InvocationContext.Factory.getCurrentInstance();
        SoaHeader soaHeader = context.getHeader();

        final ByteBuf requestBuf = Unpooled.directBuffer(8192);
        final TSoaTransport outputSoaTransport = new TSoaTransport(requestBuf);

        TSoaServiceProtocol outputProtocol;
        ByteBuf responseBuf = null;

        try {
            outputProtocol = new TSoaServiceProtocol(outputSoaTransport, true);
            requestSerializer.write(request, outputProtocol);
            outputProtocol.writeMessageEnd();

            //requestBuf就是序列化后的请求，当前线程需要将它反序列化，并找到正确的processor来处理，再将结果序列化
            responseBuf = callLocalService(requestBuf);

            final TSoaTransport inputSoaTransport = new TSoaTransport(responseBuf);
            TSoaServiceProtocol inputProtocol = new TSoaServiceProtocol(inputSoaTransport, true);

            TMessage msg = inputProtocol.readMessageBegin();
            if (TMessageType.EXCEPTION == msg.type) {
                TApplicationException x = TApplicationException.read(inputProtocol);
                inputProtocol.readMessageEnd();
                throw x;
            } else if (context.getSeqid() != msg.seqid) {
                throw new TApplicationException(4, soaHeader.getMethodName() + " failed: out of sequence response");
            } else {
                if ("0000".equals(soaHeader.getRespCode().get())) {
                    responseSerializer.read(response, inputProtocol);
                    inputProtocol.readMessageEnd();
                } else {
                    throw new SoaException(soaHeader.getRespCode().get(), soaHeader.getRespMessage().get());
                }

                return response;

            }
        } catch (SoaException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;

        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);

            throw new SoaException(SoaBaseCode.UnKnown);
        } finally {
            outputSoaTransport.close();

            if (requestBuf.refCnt() > 0)
                requestBuf.release();

            if (responseBuf != null)
                responseBuf.release();
        }
    }


    /**
     * 异步调用，返回一个Future,等待该future完成或异常
     *
     * @param request
     * @param response
     * @param requestSerializer
     * @param responseSerializer
     * @param <REQ>
     * @param <RESP>
     * @return
     * @throws TException
     */
    public <REQ, RESP> Future<RESP> sendAsync(REQ request, RESP response, TBeanSerializer<REQ> requestSerializer, TBeanSerializer<RESP> responseSerializer, long timeout) throws TException {

        throw new SoaException("系统错误", "SoaLocalConnectionImpl的sendAsync未实现");
    }

}
