package com.example.demoMDB.config;


import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Configuration
public class MultiTenantManager {

    private  ThreadLocal<String> currentTenant = new ThreadLocal<>();
    private  Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>();
    private Function<String, DataSourceProperties> tenantResolver;
    private AbstractRoutingDataSource multiTenantDataSource;


    @Bean
    @Primary
    public DataSource dataSource() {

        multiTenantDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return currentTenant.get();
            }
        };
        multiTenantDataSource.setTargetDataSources(tenantDataSources);
        multiTenantDataSource.setDefaultTargetDataSource(defaultDataSource());
        multiTenantDataSource.afterPropertiesSet();
        return multiTenantDataSource;
    }

    public void setTenantResolver(Function<String, DataSourceProperties> tenantResolver) {
        this.tenantResolver = tenantResolver;
    }

    public void setCurrentTenant(String tenantId) throws SQLException {
        if (tenantIsAbsent(tenantId)) {
            if (tenantResolver != null) {
                DataSourceProperties properties;
                try {
                    properties = tenantResolver.apply(tenantId);
                    //log.debug("[d] Datasource properties resolved for tenant ID '{}'", tenantId);
                    String url = properties.getUrl();
                    String username = properties.getUsername();
                    String password = properties.getPassword();
                    String driverClassName = properties.getDriverClassName();

                    addTenant(tenantId, url, username, password,driverClassName);
                } catch (Exception e) {
                }
            } else {
            }
        }
        currentTenant.set(tenantId);

    }

    public void addTenant(String tenantId, String url, String username, String password,String driverClassName) throws SQLException {

        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();

        try(Connection c = dataSource.getConnection()) {
            tenantDataSources.put(tenantId, dataSource);
            multiTenantDataSource.afterPropertiesSet();

        }
    }

    public DataSource removeTenant(String tenantId) {
        Object removedDataSource = tenantDataSources.remove(tenantId);
        multiTenantDataSource.afterPropertiesSet();
        return (DataSource) removedDataSource;
    }

    public boolean tenantIsAbsent(String tenantId) {
        return !tenantDataSources.containsKey(tenantId);
    }

    public Collection<Object> getTenantList() {
        return tenantDataSources.keySet();
    }

    private DriverManagerDataSource defaultDataSource() {

        DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();

        defaultDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        defaultDataSource.setUrl("jdbc:mysql://localhost:3308/test_database");
        defaultDataSource.setUsername("root");
        defaultDataSource.setPassword("");

        return defaultDataSource;
    }
}

