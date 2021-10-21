package com.alibaba.chaosblade.exec.plugin.qredis;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class QRedisEnhancer extends BeforeEnhancer {

    private static final Logger LOGGER = LoggerFactory.getLogger(QRedisEnhancer.class);

    @Override
    public EnhancerModel doBeforeAdvice(ClassLoader classLoader, String className, Object object, Method method, Object[] methodArguments) throws Exception {
        if (object == null) {
            LOGGER.warn("The necessary parameter is null.");
            return null;
        }
        String namespace = extractNamespace(object);
        if (namespace == null || namespace.isEmpty()) return null;
        MatcherModel matcherModel = new MatcherModel();
        matcherModel.add(QRedisConstant.NAMESPACE, namespace);
        return new EnhancerModel(classLoader, matcherModel);
    }

    private String extractNamespace(Object object) {
        try {
            // 这里获取的objectClassName和doBeforeAdvice方法参数的className不同。
            // 比如一个子类对象调用父类方法，className获取的是父类名，objectClassName是子类名
            String objectClassName = object.getClass().getName();
            if (objectClassName.equals(QRedisConstant.QCLIENT_REDIS_CLASS_NAME)) {
                Object command = ReflectUtil.getSuperclassFieldValue(object, "command", false);
                if (command == null) return null;
                return ReflectUtil.invokeMethod(command, "getLocator", new Object[0], false);
            } else if (objectClassName.endsWith("AdvancedSedis2") || objectClassName.endsWith("AdvancedSedis3")) {
                return ReflectUtil.getSuperclassFieldValue(object, "namespace", false);
            } else if (objectClassName.endsWith("AdvancedSedis")) {
                Object sedisObject = ReflectUtil.getFieldValue(object, "sedis", false);
                if (sedisObject == null) return null;
                return ReflectUtil.getFieldValue(sedisObject, "namespace", false);
            } else {
                return ReflectUtil.getFieldValue(object, "namespace", false);
            }
        } catch (Exception e) {
            LOGGER.error("extract qredis namespace error", e);
            return null;
        }
    }
}
