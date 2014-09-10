package com.wolf.httpClient.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sam on 14-9-9.
 */
@XmlRootElement(name = "location")
public class BaiduGeo {

    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
