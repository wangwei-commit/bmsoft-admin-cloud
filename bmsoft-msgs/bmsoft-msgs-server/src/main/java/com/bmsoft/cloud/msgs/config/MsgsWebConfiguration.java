package com.bmsoft.cloud.msgs.config;

import com.bmsoft.cloud.boot.config.BaseConfig;
import com.bmsoft.cloud.log.event.SysLogListener;
import com.bmsoft.cloud.oauth.api.LogApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bmsoft
 * @createTime 2017-12-15 14:42
 */
@Configuration
public class MsgsWebConfiguration extends BaseConfig {
    /**
     * bmsoft.log.enabled = true 并且 bmsoft.log.type=DB时实例该类
     *
     * @param logApi
     * @return
     */
    @Bean
    @ConditionalOnExpression("${bmsoft.log.enabled:true} && 'DB'.equals('${bmsoft.log.type:LOGGER}')")
    public SysLogListener sysLogListener(LogApi logApi) {
        return new SysLogListener((log) -> logApi.save(log));
    }
}
