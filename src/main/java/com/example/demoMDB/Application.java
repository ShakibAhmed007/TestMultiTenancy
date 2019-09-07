package com.example.demoMDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class})

public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	public DataSource dataSource() {
//
//		TenantAwareRoutingSource dataSource = new TenantAwareRoutingSource();
//		Map<Object,Object> targetDataSources = new HashMap<>();
//		targetDataSources.put("DEFAULTDB", getDefaultDb());
//		dataSource.setTargetDataSources(targetDataSources);
//		dataSource.afterPropertiesSet();
//		return dataSource;
//	}
//
//	public DataSource getDefaultDb() {
//
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3308/test_database");
//		dataSource.setUsername("root");
//		dataSource.setPassword("");
//
//		return dataSource;
//	}
}
