package com.wolf.common.thread.impl;

import com.wolf.common.thread.AbstractThridCreateGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sam on 2020/4/21.
 */
@Slf4j
public abstract class AbstractLocalDBGroupTask extends AbstractThridCreateGroup {


    protected String rcGroupId;

    protected String ncGroupId;



    public AbstractLocalDBGroupTask setRcGroupId(String rcGroupId) {
        this.rcGroupId = rcGroupId;
        return this;
    }

    public AbstractLocalDBGroupTask setNcGroupId(String ncGroupId) {
        this.ncGroupId = ncGroupId;
        return this;
    }




}
