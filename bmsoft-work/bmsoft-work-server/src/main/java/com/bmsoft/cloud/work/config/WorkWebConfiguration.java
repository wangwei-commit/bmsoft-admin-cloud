package com.bmsoft.cloud.work.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bmsoft.cloud.boot.config.BaseConfig;
import com.bmsoft.cloud.log.event.SysLogListener;
import com.bmsoft.cloud.oauth.api.LogApi;

/**
 * 作业管理服务-Web配置
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Configuration
public class WorkWebConfiguration extends BaseConfig {

    /**
    * bmsoft.log.enabled = true 并且 bmsoft.log.type=DB时实例该类
    *
    * @param optLogService
    * @return
    */
    @Bean
    @ConditionalOnExpression("${bmsoft.log.enabled:true} && 'DB'.equals('${bmsoft.log.type:LOGGER}')")
    public SysLogListener sysLogListener(LogApi logApi) {
        return new SysLogListener((log) -> logApi.save(log));
    }
}
