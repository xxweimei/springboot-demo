package com.example.demo.service;

import com.example.demo.model.Demo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * functional description
 * Created by Sandy
 * on 2017/7/6.
 */
@Service
@Slf4j
public class DemoService {

    private final JedisCluster jedisCluster;

    @Autowired
    public DemoService(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public String redisGet(String key) {
        return jedisCluster.get(key);
    }

    public Demo db(){
        return new Demo();
    }

    @Async
    public void async(){

        log.info("async execute finish");
    }
}
