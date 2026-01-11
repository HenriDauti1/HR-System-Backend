package com.hr_system.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${db.readonly.username}")
    private String readonlyUsername;
    
    @Value("${db.readonly.password}")
    private String readonlyPassword;
    
    @Value("${db.crud.username}")
    private String crudUsername;
    
    @Value("${db.crud.password}")
    private String crudPassword;
    
    public DataSource createDataSource(String username, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(5);
        dataSource.setSchema("HR Database");
        return dataSource;
    }
    
    public DataSource getReadOnlyDataSource() {
        return createDataSource(readonlyUsername, readonlyPassword);
    }
    
    public DataSource getCrudDataSource() {
        return createDataSource(crudUsername, crudPassword);
    }
}