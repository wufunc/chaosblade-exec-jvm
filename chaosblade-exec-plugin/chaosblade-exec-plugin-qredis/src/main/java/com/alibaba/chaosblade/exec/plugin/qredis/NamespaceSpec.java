package com.alibaba.chaosblade.exec.plugin.qredis;

import com.alibaba.chaosblade.exec.common.model.matcher.BasePredicateMatcherSpec;

public class NamespaceSpec extends BasePredicateMatcherSpec {
    @Override
    public String getName() {
        return QRedisConstant.NAMESPACE;
    }

    @Override
    public String getDesc() {
        return "redis locator namespace or ip:port";
    }

    @Override
    public boolean noArgs() {
        return false;
    }

    @Override
    public boolean required() {
        return false;
    }
}
