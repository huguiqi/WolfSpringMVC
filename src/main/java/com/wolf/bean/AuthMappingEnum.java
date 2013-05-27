package com.wolf.bean;

/**
 * Author: sam.hu
 * Title: enum class
 * Desc: 传输映射系统权限
 * CreateTime: 11-11-20 下午10:51
 */
public enum AuthMappingEnum {
    ROLE_USER("0"),
    ROLE_ADMIN("1"),
    ROLE_MEMBER("2");

    private String alias;

    private AuthMappingEnum(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
       return alias;
    }



}
