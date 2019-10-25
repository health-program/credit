package com.paladin.credit.core;

import com.paladin.common.core.CommonHandlerExceptionResolver;
import com.paladin.common.core.TontoDialect;
import com.paladin.common.service.syst.SysUserService;
import com.paladin.framework.core.configuration.shiro.ShiroCasProperties;
import io.buji.pac4j.realm.Pac4jRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

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

	@Bean("casRealm")
	@ConditionalOnProperty(prefix = "paladin", value = "cas-enabled", havingValue = "true", matchIfMissing = false)
	public Pac4jRealm getCasRealm(ShiroCasProperties shiroCasProperties) {
		return new CreditCasUserRealm(shiroCasProperties);
	}

	@Bean("localRealm")
	public AuthorizingRealm getLocalRealm(SysUserService sysUserService, CreditUserSessionFactory sessionFactory) {
		return new CreditUserRealm(sysUserService, sessionFactory);
	}

}