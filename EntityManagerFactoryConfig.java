package com.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

/**@Configuration is used to declare one or more @bean methods**/
@Configuration
public class EntityManagerFactoryConfig {
	
	/**@Bean tells that a method produces a bean which should be managed by spring container **/
	@Bean
	public LocalEntityManagerFactoryBean getEntityManagerFactory() {
		LocalEntityManagerFactoryBean bean = new LocalEntityManagerFactoryBean();
		bean.setPersistenceUnitName("asset_management");
		return bean;
	}
}
