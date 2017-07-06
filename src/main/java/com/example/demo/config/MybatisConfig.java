package com.example.demo.config;

import com.example.demo.commons.utils.ConfigUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * functional description
 * Created by Sandy
 * on 2017/3/16.
 */
@Configuration
@MapperScan(basePackages = {"com.example.demo.database.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {

    @Bean(destroyMethod = "close")
    HikariDataSource readDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("readHikariPool");
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.setAutoCommit(false);
        config.setConnectionTimeout(1000);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(200);
        Properties properties = new Properties();
        properties.setProperty("url", ConfigUtils.conf().getString("jdbc.read.url"));
        properties.setProperty("user", ConfigUtils.conf().getString("jdbc.read.user"));
        properties.setProperty("password", ConfigUtils.conf().getString("jdbc.read.password"));
        config.setDataSourceProperties(properties);
        return new HikariDataSource(config);
    }

    @Bean(destroyMethod = "close")
    HikariDataSource writeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("writeHikariPool");
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.setAutoCommit(false);
        config.setConnectionTimeout(1000);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(200);
        Properties properties = new Properties();
        properties.setProperty("url", ConfigUtils.conf().getString("jdbc.write.url"));
        properties.setProperty("user", ConfigUtils.conf().getString("jdbc.write.user"));
        properties.setProperty("password", ConfigUtils.conf().getString("jdbc.write.password"));
        config.setDataSourceProperties(properties);
        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    @DependsOn({"writeDataSource", "readDataSource"})
    public DynamicDataSource dataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", writeDataSource());
        dataSourceMap.put("read", readDataSource());
        dataSource.setTargetDataSources(dataSourceMap);
        dataSource.setDefaultTargetDataSource(writeDataSource());
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
