package com.wolf.common.thread;

@FunctionalInterface
public interface IMTaskCallback<P> {

    void execute(P p);
}
