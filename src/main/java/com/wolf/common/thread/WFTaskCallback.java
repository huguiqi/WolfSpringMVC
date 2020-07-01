package com.wolf.common.thread;

@FunctionalInterface
public interface WFTaskCallback<P> {

    void execute(P p);
}
