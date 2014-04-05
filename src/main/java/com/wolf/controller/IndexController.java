package com.wolf.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
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

}
