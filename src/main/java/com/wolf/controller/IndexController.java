package com.wolf.controller;


import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    protected static Logger logger = Logger.getLogger("controller");

    /**
     * 跳转到commonpage页面
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getCommonPage() {
        logger.debug("Received request to show common page");
        return "index";
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> test(Map<String,Object> params){
        logger.debug("start restController test.......");
//        Map<String,Object> map = new HashMap<>();
        params.put("name","xiaohei");
        return params;
    }

}
