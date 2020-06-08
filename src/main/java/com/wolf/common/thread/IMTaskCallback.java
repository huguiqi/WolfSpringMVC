package com.huazhu.im.imadminapi.service.thread;

@FunctionalInterface
public interface IMTaskCallback<P> {

    void execute(P p);
}
