package com.wolf.httpClient;

import com.wolf.common.utils.JaxbUtils;
import com.wolf.common.utils.PinYin;
import com.wolf.httpClient.model.BaiduGeo;
import com.wolf.httpClient.model.BaiduGeoResult;
import com.wolf.httpClient.model.CityBean;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 14-9-10.
 */
public class HttpClientTest {

    @Test
    public void testData(){

        String cities = "三门峡,上饶,中山,临海,丹江口,乌兰察布,乌兰浩特,乌镇,伦敦,佛山,北海,天台县,宜春,宿州,巢湖,平遥县,延边朝鲜,承德,攀枝花,晋中,曲靖,柳州,株洲,桐庐县,沧州,浙江,浦江县,温岭,滁州,玉山县,绵阳,肇庆,胶州,荷泽,营口";
        CloseableHttpResponse response = null;
        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            BaiduGeoResult baiduGeoResult = new BaiduGeoResult();
            List<CityBean> cityBeans = new ArrayList<CityBean>();
            for(String city:cities.split(",")){
                HttpGet httpget = new HttpGet(
                        "http://api.map.baidu.com/geocoder/v2/?address="+city+"&ak=7be5d92fa593f90d435cc2394fe080c0&callback=showLocation");
                response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resutStr = EntityUtils.toString(entity);
                    BaiduGeo geo = JaxbUtils.convertToObject(resutStr.substring(resutStr.indexOf("<location>"), resutStr.indexOf("</location>") +11), BaiduGeo.class);
                    CityBean cityBean = new CityBean();
                    cityBean.setName(city);
                    cityBean.setNamePinyin(PinYin.getPinYin(city));
                    cityBean.setLatitude(geo.getLat());
                    cityBean.setLongitude(geo.getLng());
                    cityBeans.add(cityBean);
                }
            }
            baiduGeoResult.setCityBeans(cityBeans);
            System.out.println("vm:"+ JaxbUtils.convertToXmlString(baiduGeoResult));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();  //todo set log
            }
        }

    }
}
