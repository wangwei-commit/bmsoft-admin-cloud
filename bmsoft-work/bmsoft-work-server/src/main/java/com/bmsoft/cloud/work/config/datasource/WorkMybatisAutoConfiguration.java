package com.bmsoft.cloud.work.config.datasource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.bmsoft.cloud.database.datasource.BaseMybatisConfiguration;
import com.bmsoft.cloud.database.mybatis.auth.DataScopeInterceptor;
import com.bmsoft.cloud.database.properties.DatabaseProperties;
import com.bmsoft.cloud.oauth.api.UserApi;
import com.bmsoft.cloud.utils.SpringUtils;

/**
 * 作业管理服务-Mybatis 常用重用拦截器
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Configuration
@EnableConfigurationProperties({ DatabaseProperties.class })
public class WorkMybatisAutoConfiguration extends BaseMybatisConfiguration {

	public WorkMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
		super(databaseProperties);
	}

	/**
	 * 数据权限插件
	 *
	 * @return DataScopeInterceptor
	 */
	@Order(10)
	@Bean
	@ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "isDataScope", havingValue = "true", matchIfMissing = true)
	public DataScopeInterceptor dataScopeInterceptor() {
		return new DataScopeInterceptor((userId) -> SpringUtils.getBean(UserApi.class).getDataScopeById(userId));
	}

}
