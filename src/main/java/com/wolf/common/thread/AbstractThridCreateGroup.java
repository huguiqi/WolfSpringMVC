package com.wolf.common.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by sam on 2020/4/21.
 */
@Slf4j
public abstract class AbstractThridCreateGroup extends BaseBusinessRunnable {


    public abstract boolean hasCreateGroup();

    public abstract void valid();
}
