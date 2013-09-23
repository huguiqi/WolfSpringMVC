package com.wolf.bean;

import javapns.devices.Device;

public class DeviceBean {

    private Device device;
    private boolean isCorrect;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }
}
