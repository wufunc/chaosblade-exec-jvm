package com.alibaba.chaosblade.exec.plugin.http.asynchttpclient;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.chaosblade.exec.common.constant.ModelConstant;
import com.alibaba.chaosblade.exec.common.util.BusinessParamUtil;
import com.alibaba.chaosblade.exec.spi.BusinessDataGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.context.GlobalContext;
import com.alibaba.chaosblade.exec.common.util.FlagUtil;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import com.alibaba.chaosblade.exec.plugin.http.HttpConstant;
import com.alibaba.chaosblade.exec.plugin.http.enhancer.InternalPointCut;

/**
 * @author shizhi.zhu@qunar.com
 */
@InternalPointCut(className = "com.ning.http.client.providers.netty.request.NettyRequestSender",
        methodName = "newNettyRequestAndResponseFuture")
public class NettyRequestSenderEnhancer extends BeforeEnhancer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRequestSenderEnhancer.class);
    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    @Override
    public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method,
                                        Object[] methodArguments) throws Exception {
        if (!shouldAddCallPoint()) {
            return null;
        }
        if (methodArguments.length < 2) {
            LOGGER.warn("argument's length less than 2, can't find Request, className:{}, methodName:{}", className,
                    method.getName());
            return null;
        }

        Object request = methodArguments[0];
        if (!request.getClass().getName().equals("com.ning.http.client.RequestBuilderBase$RequestImpl")) {
            LOGGER.warn("argument is not RequestImpl, className:{}, methodName:{}", className, method.getName());
            return null;
        }
        final Object headers = ReflectUtil.invokeMethod(request, "getHeaders");
        String id = String.valueOf(ID_GENERATOR.incrementAndGet());
        ReflectUtil.invokeMethod(headers, "add", new String[]{HttpConstant.REQUEST_ID_STACK, id});
        StackTraceElement[] stackTrace = new NullPointerException().getStackTrace();
        GlobalContext.getDefaultInstance().put(id, stackTrace);
        id = String.valueOf(ID_GENERATOR.incrementAndGet());
        ReflectUtil.invokeMethod(headers, "add", new String[]{HttpConstant.REQUEST_ID_BUSINESSPARAM, id});
        GlobalContext.getDefaultInstance().put(id, BusinessParamUtil.getAndParse(ModelConstant.HTTP_TARGET, new BusinessDataGetter() {
            @Override
            public String get(String key) throws Exception {
                List<String> values = (List<String>) ReflectUtil.invokeMethod(headers, "get", new Object[]{key}, false);
                if (values != null && !values.isEmpty()) {
                    return values.get(0);
                }
                return null;
            }
        }));
        return null;
    }

    protected boolean shouldAddCallPoint() {
        return FlagUtil.hasFlag("http", HttpConstant.CALL_POINT_KEY);
    }
}
