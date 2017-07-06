package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * functional description
 * Created by Sandy
 * on 2017/7/6.
 */
@Service
public class DemoService {

    private final JedisCluster jedisCluster;

    @Autowired
    public DemoService(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public String redisGet(String key) {
        return jedisCluster.get(key);
    }
}
