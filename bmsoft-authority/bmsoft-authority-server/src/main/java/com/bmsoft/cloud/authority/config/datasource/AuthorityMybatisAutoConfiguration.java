package com.bmsoft.cloud.authority.config.datasource;


import com.bmsoft.cloud.authority.service.auth.UserService;
import com.bmsoft.cloud.database.datasource.BaseMybatisConfiguration;
import com.bmsoft.cloud.database.mybatis.auth.DataScopeInterceptor;
import com.bmsoft.cloud.database.properties.DatabaseProperties;
import com.bmsoft.cloud.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 配置一些 Mybatis 常用重用拦截器
 *
 * @author bmsoft
 * @createTime 2017-11-18 0:34
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({DatabaseProperties.class})
public class AuthorityMybatisAutoConfiguration extends BaseMybatisConfiguration {

    public AuthorityMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
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
        return new DataScopeInterceptor((userId) -> SpringUtils.getBean(UserService.class).getDataScopeById(userId));
    }

}
