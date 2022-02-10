package com.alibaba.chaosblade.exec.common.util;

import com.alibaba.chaosblade.exec.common.center.ManagerFactory;
import com.alibaba.chaosblade.exec.spi.TraceIdGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wufunc@gmail.com
 * 2022/2/10
 **/
public class TraceIdUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraceIdUtil.class);

    public static String getTraceId() {
        List<Object> objects = ManagerFactory.spiServiceManager().getServices(TraceIdGetter.class.getName(), Thread.currentThread().getContextClassLoader());
        if (objects == null || objects.isEmpty()) {
            return null;
        }
        String result = null;
        for (Object object : objects) {
            try {
                result = ReflectUtil.invokeMethod(object, "getTraceId", new Object[]{}, true);
            } catch (Exception e) {
                LOGGER.warn("get traceId from spi class error,class :{}", object.getClass().getName(), e);
            }
            if (!StringUtils.isEmpty(result)) {
                return result;
            }
        }
        return result;
    }
}
