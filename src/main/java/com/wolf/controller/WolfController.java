package com.wolf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "wolfController")
@RequestMapping(value = "/wolf")
public class WolfController {

    @RequestMapping(value = "/fullcalendViews",method = RequestMethod.GET)
    public String basicViews(){

        return "/resources/wolf/fullcalend/basicViews";
    }

    @RequestMapping(value = "/webWorkersViews",method = RequestMethod.GET)
    public String webWorkersViews(){

        return "/resources/wolf/webworkers/webWorkersView";
    }


}
