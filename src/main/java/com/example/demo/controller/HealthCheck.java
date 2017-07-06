package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * functional description
 * Created by ShengyangKong
 * on 2016/3/9.
 */
@RestController
@RequestMapping(produces = "text/plain;charset=UTF-8")
public class HealthCheck {

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<String> status() {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
