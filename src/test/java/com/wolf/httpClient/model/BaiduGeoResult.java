package com.wolf.httpClient.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by sam on 14-9-9.
 */
@XmlRootElement
public class BaiduGeoResult {

    private List<CityBean> cityBeans;

    public List<CityBean> getCityBeans() {
        return cityBeans;
    }

    public void setCityBeans(List<CityBean> cityBeans) {
        this.cityBeans = cityBeans;
    }
}
