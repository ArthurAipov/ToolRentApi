package com.example.firstapi.DataBase;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceHikariConfig {

    @Value("${spring.datasource.url}")
    private String URL;

    @Value("${spring.datasource.username}")
    private String USERNAME;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @Value("${db.driver}")
    private String DRIVER;

    @Value("${spring.profiles.active}")
    private String PROFILE;

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        // Creation config
        HikariConfig hikariConfig = new HikariConfig();

        // Base settings
        hikariConfig.setJdbcUrl(URL);
        hikariConfig.setDriverClassName(DRIVER);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setUsername(USERNAME);

        // Custom settings about connection pull
        hikariConfig.setMaximumPoolSize(30);
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setConnectionTimeout(10_000);
        hikariConfig.setMaxLifetime(1_800_000);
        hikariConfig.setIdleTimeout(600_000);
        hikariConfig.setPoolName("Tool_rent");


        return new HikariDataSource(hikariConfig);
    }
}
