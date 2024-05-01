package com.smbc.epix.transaction.services.configuration;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@MapperScan(basePackages = "com.smbc.epix.transaction.services.dao.foxdb", sqlSessionFactoryRef = "foxdbSqlSessionFactory")
public class FoxdbDataConfig {

	@Autowired
	ResourceLoader resourceLoader;
	@Value("${application.fox.datasource.driver.class-name}")
    String dataSourceDriverName;
	@Value("${application.fox.datasource.url}")
    String dataSourceUrl;
	@Value("${application.fox.datasource.username}")
    String dataSourceUsername;
	@Value("${application.fox.datasource.password}")
    String dataSourcePassword;
	
	@Bean(name = "foxdbDatasource")
	public DataSource foxdbDatasource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(dataSourceDriverName);
		driverManagerDataSource.setUrl(dataSourceUrl);
		driverManagerDataSource.setUsername(dataSourceUsername);
		driverManagerDataSource.setPassword(dataSourcePassword);
		return driverManagerDataSource;
	}
	
	@Bean(name="foxdbSqlSessionFactory")
	public SqlSessionFactoryBean foxdbSqlSessionFactory(@Qualifier("foxdbDatasource") DataSource foxdbDatasource) {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(foxdbDatasource); 
		return sqlSessionFactoryBean;
	}
	
	@Bean(name="foxdbtransactionManager")
	public DataSourceTransactionManager foxdbtransactionManager(@Qualifier("foxdbDatasource") DataSource foxdbDatasource) {
		DataSourceTransactionManager dataSourceTransactionManager=new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(foxdbDatasource);
		return dataSourceTransactionManager;
	}
}