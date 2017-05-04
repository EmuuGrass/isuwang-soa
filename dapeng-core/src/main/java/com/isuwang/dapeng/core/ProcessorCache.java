package com.isuwang.dapeng.core;

import com.isuwang.dapeng.core.version.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tangliu on 17/4/6.
 */
public class ProcessorCache {

    private static final Map<ProcessorKey, SoaBaseProcessor<?>> processorMap = new ConcurrentHashMap<>();

    public static Map<ProcessorKey, SoaBaseProcessor<?>> getProcessorMap() {
        return processorMap;
    }

    /**
     * 根据serviceName, versionName获取匹配的服务处理器
     *
     * @param serviceName
     * @param versionName
     * @return
     */
    public static List<SoaBaseProcessor<?>> getMatchedProcessors(String serviceName, String versionName) {

        List<SoaBaseProcessor<?>> matchedProcessors = new ArrayList<>();
        processorMap.forEach((key, processor) -> {

            if (key.getServiceName().equals(serviceName) && Version.toVersion(versionName).compatibleTo(Version.toVersion(key.getVersionName())))
                matchedProcessors.add(processor);
        });
        return matchedProcessors;
    }


    static Logger logger = LoggerFactory.getLogger(ProcessorCache.class);

    /**
     * 根据serviceName, versionName获取匹配的服务处理器
     *
     * @param serviceName
     * @param versionName
     * @return
     */
    public static List<SoaBaseProcessor<?>> getMatchedProcessorsOfCurrClassLoader(String serviceName, String versionName, ClassLoader classLoader) {

        List<SoaBaseProcessor<?>> matchedProcessors = new ArrayList<>();
        processorMap.forEach((key, processor) -> {

            if (key.getServiceName().equals(serviceName) && Version.toVersion(versionName).compatibleTo(Version.toVersion(key.getVersionName()))) {
                if (processor.getClass().getClassLoader() == classLoader)
                    matchedProcessors.add(processor);
            }
        });
        return matchedProcessors;
    }
}
