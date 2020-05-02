package com.bmsoft.cloud.authority.config;

import com.bmsoft.cloud.authority.service.common.OptLogService;
import com.bmsoft.cloud.boot.config.BaseConfig;
import com.bmsoft.cloud.log.event.SysLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bmsoft
 * @createTime 2017-12-15 14:42
 */
@Configuration
public class AuthorityWebConfiguration extends BaseConfig {

    /**
     * bmsoft.log.enabled = true 并且 bmsoft.log.type=DB时实例该类
     *
     * @param optLogService
     * @return
     */
    @Bean
    @ConditionalOnExpression("${bmsoft.log.enabled:true} && 'DB'.equals('${bmsoft.log.type:LOGGER}')")
    public SysLogListener sysLogListener(OptLogService optLogService) {
        return new SysLogListener((log) -> optLogService.save(log));
    }
}
