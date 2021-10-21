package com.alibaba.chaosblade.exec.plugin.qredis;

import com.alibaba.chaosblade.exec.common.aop.Enhancer;
import com.alibaba.chaosblade.exec.common.aop.Plugin;
import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.model.ModelSpec;

public class QRedisPlugin implements Plugin {
    @Override
    public String getName() {
        return QRedisConstant.TARGET;
    }

    @Override
    public ModelSpec getModelSpec() {
        return new QRedisModelSpec();
    }

    @Override
    public PointCut getPointCut() {
        return new QRedisPointCut();
    }

    @Override
    public Enhancer getEnhancer() {
        return new QRedisEnhancer();
    }
}
