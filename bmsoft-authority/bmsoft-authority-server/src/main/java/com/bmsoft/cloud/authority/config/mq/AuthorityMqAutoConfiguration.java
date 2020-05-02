package com.bmsoft.cloud.authority.config.mq;

import com.alibaba.fastjson.JSONObject;
import com.bmsoft.cloud.authority.dto.auth.SystemApiScanSaveDTO;
import com.bmsoft.cloud.authority.service.auth.SystemApiService;
import com.bmsoft.cloud.context.BaseContextHandler;
import com.bmsoft.cloud.mq.constant.QueueConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * 消息队列配置
 *
 * @author bmsoft
 * @date 2019/12/17
 */
@Configuration
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "bmsoft.rabbitmq", name = "enabled", havingValue = "true")
public class AuthorityMqAutoConfiguration {
    private final SystemApiService systemApiService;

    @Bean
    public Queue apiResourceQueue() {
        Queue queue = new Queue(QueueConstants.QUEUE_SCAN_API_RESOURCE);
        log.info("Query {} [{}]", QueueConstants.QUEUE_SCAN_API_RESOURCE, queue);
        return queue;
    }

    @RabbitListener(queues = QueueConstants.QUEUE_SCAN_API_RESOURCE)
    public void scanApiResourceRabbitListener(@Payload String param) {
        SystemApiScanSaveDTO scan = JSONObject.parseObject(param, SystemApiScanSaveDTO.class);
        BaseContextHandler.setTenant(scan.getTenant());

        this.systemApiService.batchSave(scan);
    }

}
