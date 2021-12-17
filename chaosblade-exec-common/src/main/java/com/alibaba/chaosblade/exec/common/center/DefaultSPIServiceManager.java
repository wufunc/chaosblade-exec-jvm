package com.alibaba.chaosblade.exec.common.center;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DefaultSPIServiceManager implements SPIServiceManager {
    private static Map<String, List<Object>> spiMap;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSPIServiceManager.class);

    static {
        spiMap = new HashMap<String, List<Object>>();
    }

    @Override
    public void load() {

    }

    @Override
    public List<Object> getServices(String className, ClassLoader classLoader) {
        if (spiMap.containsKey(className)) {
            return spiMap.get(className);
        }
        synchronized (this) {
            if (spiMap.containsKey(className)) {
                return spiMap.get(className);
            }
            List<Object> services = loadService(className, classLoader);
            spiMap.put(className, services);
            return services;
        }
    }

    public List<Object> loadService(String className, ClassLoader classLoader) {
        Class clazz;
        try {
            clazz = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            LOGGER.warn("cant find class:{}", className);
            return Collections.EMPTY_LIST;
        }
        ServiceLoader serviceLoader = ServiceLoader.load(clazz, classLoader);
        List<Object> objects = new ArrayList<Object>();
        for (Object object : serviceLoader) {
            objects.add(object);
        }
        return objects;
    }

    @Override
    public void unload() {
        spiMap.clear();
    }
}
