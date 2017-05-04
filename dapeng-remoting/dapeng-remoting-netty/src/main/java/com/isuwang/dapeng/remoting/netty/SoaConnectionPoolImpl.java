package com.isuwang.dapeng.remoting.netty;

import com.isuwang.dapeng.core.*;
import com.isuwang.dapeng.remoting.SoaConnection;
import com.isuwang.dapeng.remoting.SoaConnectionPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoaConnectionPoolImpl implements SoaConnectionPool {

    private static final SoaConnectionPoolImpl pool = new SoaConnectionPoolImpl();

    static {
        IdleConnectionManager connectionManager = new IdleConnectionManager();
        connectionManager.start();
    }

    public static SoaConnectionPool getInstance() {
        return pool;
    }

    private Map<String, SoaConnectionImpl> connectionMap = new ConcurrentHashMap<>();

    @Override
    public synchronized SoaConnection getConnection() throws SoaException {
        InvocationContext context = InvocationContext.Factory.getCurrentInstance();

        if (context.getCalleeIp() == null || context.getCalleePort() <= 0)
            throw new SoaException(SoaBaseCode.NotFoundServer);

        if (!context.getHeader().isAsyncCall() && SoaSystemEnvProperties.SOA_CALL_LOCAL_ENABLE
                && ProcessorCache.getMatchedProcessorsOfCurrClassLoader(context.getHeader().getServiceName(), context.getHeader().getVersionName(), SoaConnectionPoolImpl.class.getClassLoader()).size() > 0)
            return new SoaLocalConnectionImpl();

        String connectKey = context.getCalleeIp() + ":" + String.valueOf(context.getCalleePort());

        if (connectionMap.containsKey(connectKey)) {
            return connectionMap.get(connectKey);
        }

        SoaConnectionImpl soaConnection = new SoaConnectionImpl(context.getCalleeIp(), context.getCalleePort());

        connectionMap.put(connectKey, soaConnection);

        return soaConnection;
    }

    /**
     * 删除链接
     *
     * @throws SoaException
     */
    @Override
    public synchronized void removeConnection() throws SoaException {

        InvocationContext context = InvocationContext.Factory.getCurrentInstance();

        if (context.getCalleeIp() == null || context.getCalleePort() <= 0)
            return;

        String connectKey = context.getCalleeIp() + ":" + String.valueOf(context.getCalleePort());

        if (connectionMap.containsKey(connectKey)) {
            connectionMap.remove(connectKey);
        }
    }

}
