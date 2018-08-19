package com.dx.proxypool.service;

/**
 * Created by daixiang on 2018/8/19.
 */

public abstract class ServiceTemplate<T> {

    protected ServiceTemplate() {
    }

    protected abstract void beforeProcess();

    protected abstract T process();

    protected void afterProcess() {
    }

    protected abstract void onSuccess();

    public T execute() {
        return this.doExecute();
    }

    private T doExecute() {
        Object var = null;
        try {
            this.beforeProcess();
            T result = this.process();
            var = result;
            this.onSuccess();
        } catch (Exception e) {
            var = null;
        } finally {
            this.afterProcess();
        }
        return (T) var;
    }
}
