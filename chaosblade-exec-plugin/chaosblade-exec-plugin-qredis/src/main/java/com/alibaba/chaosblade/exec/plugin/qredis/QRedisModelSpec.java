package com.alibaba.chaosblade.exec.plugin.qredis;

import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

import java.util.ArrayList;
import java.util.List;

public class QRedisModelSpec extends FrameworkModelSpec {
    @Override
    protected List<MatcherSpec> createNewMatcherSpecs() {
        ArrayList<MatcherSpec> matcherSpecs = new ArrayList<MatcherSpec>();
        matcherSpecs.add(new NamespaceSpec());
        return matcherSpecs;
    }

    @Override
    public String getTarget() {
        return QRedisConstant.TARGET;
    }

    @Override
    public String getShortDesc() {
        return "qunar redis experiment";
    }

    @Override
    public String getLongDesc() {
        return "qunar redis experiment, support qclient-redis and dbaccess";
    }

}
