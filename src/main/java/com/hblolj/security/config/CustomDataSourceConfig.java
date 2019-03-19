package com.hblolj.security.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author: hblolj
 * @Date: 2019/3/18 10:29
 * @Description:
 * @Version:
 **/
@Configuration
public class CustomDataSourceConfig {

    @Bean(name = "socialDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.social")
    public DataSource getSocialDataSource(){
        return DataSourceBuilder.create().build();
    }
}
