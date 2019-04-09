package com.paladin.credit.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.paladin.common.core.CommonHandlerExceptionResolver;
import com.paladin.common.core.TontoDialect;

@Configuration
@EnableConfigurationProperties(CreditProperties.class)
public class CreditConfiguration {

	@Bean
	public TontoDialect getTontoDialect() {
		return new TontoDialect();
	}

	@Bean
	public HandlerExceptionResolver getHandlerExceptionResolver() {
		return new CommonHandlerExceptionResolver();
	}
	
}