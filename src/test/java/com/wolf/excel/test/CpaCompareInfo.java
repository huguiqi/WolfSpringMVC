package com.wolf.excel.test;

/**
 * Created with IntelliJ IDEA.
 * User: sam
 * Date: 13-5-26
 * Time: 下午10:59
 * To change this template use File | Settings | File Templates.
 */
public class CpaCompareInfo {

    private String macAddress;
    private String version;
    private String desc;
    private String dateStr;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public String toString() {
        return "CpaCompareInfo{" +
                "macAddress='" + macAddress + '\'' +
                ", version='" + version + '\'' +
                ", desc='" + desc + '\'' +
                ", dateStr='" + dateStr + '\'' +
                '}';
    }
}
