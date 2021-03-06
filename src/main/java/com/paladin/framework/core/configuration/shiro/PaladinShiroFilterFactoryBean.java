package com.paladin.framework.core.configuration.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PaladinShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	private ShiroProperties shiroProperties;

	public PaladinShiroFilterFactoryBean(ShiroProperties shiroProperties) {
		super();
		this.shiroProperties = shiroProperties;
	}

	/*
	 * 在请求进入shiro流程前，判断请求是否为静态资源，如果是则跳出shiro流程
	 */
	protected AbstractShiroFilter createInstance() throws Exception {

		SecurityManager securityManager = getSecurityManager();
		if (securityManager == null) {
			String msg = "SecurityManager property must be set.";
			throw new BeanInitializationException(msg);
		}

		if (!(securityManager instanceof WebSecurityManager)) {
			String msg = "The security manager does not implement the WebSecurityManager interface.";
			throw new BeanInitializationException(msg);
		}

		FilterChainManager manager = createFilterChainManager();

		PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
		chainResolver.setFilterChainManager(manager);

		return new UnStaticShiroFilter((WebSecurityManager) securityManager, chainResolver, shiroProperties);
	}

	class UnStaticShiroFilter extends AbstractShiroFilter {

		private String[] staticPrefixs;
		private String[] authPrefixs;

		protected UnStaticShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver, ShiroProperties shiroProperties) {
			super();
			
			if (webSecurityManager == null) {
				throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
			}
			setSecurityManager(webSecurityManager);
			if (resolver != null) {
				setFilterChainResolver(resolver);
			}

			String staticRsPrefix = shiroProperties.getStaticResourcePrefix();
			if (staticRsPrefix != null && staticRsPrefix.length() > 0) {
				staticPrefixs = staticRsPrefix.split(",");
			}

			String authRsPrefix = shiroProperties.getAuthResourcePrefix();
			if (authRsPrefix != null && authRsPrefix.length() > 0) {
				authPrefixs = authRsPrefix.split(",");
			}
		}

		protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, final FilterChain chain)
				throws ServletException, IOException {

			// -------------- add by TontoZhou-----------------
			if (servletRequest instanceof HttpServletRequest) {
				HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
				String requestUrl = httpRequest.getRequestURI();

				// 过滤静态资源，防止静态资源读取session等操作
				if (authPrefixs != null) {
					for (String ap : authPrefixs) {
						if (requestUrl.startsWith(ap)) {
							super.doFilterInternal(servletRequest, servletResponse, chain);
							return;
						}
					}

					chain.doFilter(servletRequest, servletResponse);
					return;
				}

				if (staticPrefixs != null) {
					for (String sp : staticPrefixs) {
						if (requestUrl.startsWith(sp)) {
							chain.doFilter(servletRequest, servletResponse);
							return;
						}
					}
					super.doFilterInternal(servletRequest, servletResponse, chain);
				}
			}
			// -------------- add by TontoZhou -----------------
		}
	}

}
