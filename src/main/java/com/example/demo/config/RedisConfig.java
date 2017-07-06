package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * functional description
 * Created by Sandy
 * on 2017/3/16.
 */
@Configuration
public class RedisConfig {

    @Bean(destroyMethod = "close")
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("redis", 6379));
        nodes.add(new HostAndPort("redis", 6380));
        nodes.add(new HostAndPort("redis", 6381));
        nodes.add(new HostAndPort("redis1", 6379));
        nodes.add(new HostAndPort("redis1", 6380));
        nodes.add(new HostAndPort("redis1", 6381));
        return new JedisCluster(nodes);
    }
}
