package com.alibaba.chaosblade.exec.common.context;

import java.util.Map;

/**
 * @author shizhi.zhu@qunar.com
 */
public class ThreadLocalContext {

    private static ThreadLocalContext DEFAULT = new ThreadLocalContext();
    private InheritableThreadLocal<Content> local = new InheritableThreadLocal<Content>();

    public static ThreadLocalContext getInstance() {
        return DEFAULT;
    }

    public void set(Content value) {
        local.set(value);
    }

    public Content get() {
        Content content = local.get();
        if (content == null) {
            content = new ThreadLocalContext.Content();
            local.set(content);
        }
        return content;
    }

    public static class Content {
        private StackTraceElement[] stackTraceElements;
        private Map<String, Map<String, String>> businessData;
        private String traceId;

        public StackTraceElement[] getStackTraceElements() {
            return stackTraceElements;
        }

        public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
            this.stackTraceElements = stackTraceElements;
        }

        public Map<String, Map<String, String>> getBusinessData() {
            return businessData;
        }

        public void setBusinessData(Map<String, Map<String, String>> businessData) {
            this.businessData = businessData;
        }

        public String getTraceId() {
            return traceId;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }
    }
}
