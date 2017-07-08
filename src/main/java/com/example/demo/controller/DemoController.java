package com.example.demo.controller;

import com.example.demo.commons.constants.ResponseCodeConstants;
import com.example.demo.model.Demo;
import com.example.demo.model.response.BaseResponse;
import com.example.demo.model.response.GenericResponse;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * functional description
 * Created by Sandy
 * on 2017/7/6.
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class DemoController {

    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @RequestMapping(value = "/redis/{key}", method = RequestMethod.GET)
    public GenericResponse<String> redis(@PathVariable("key") String key) {

        return new GenericResponse<>(ResponseCodeConstants.SUCCESS, demoService.redisGet(key));
    }

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public GenericResponse<Demo> db() {

        return new GenericResponse<>(ResponseCodeConstants.SUCCESS, demoService.db());
    }

    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public BaseResponse async() {

        demoService.async();

        return new BaseResponse(ResponseCodeConstants.SUCCESS);
    }

    @RequestMapping(value = "/integration", method = RequestMethod.GET)
    public BaseResponse integration() {

        return new BaseResponse(ResponseCodeConstants.SUCCESS);
    }
}
