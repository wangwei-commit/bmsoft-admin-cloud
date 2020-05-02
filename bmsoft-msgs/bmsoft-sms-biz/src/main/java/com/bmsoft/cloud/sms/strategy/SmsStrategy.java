package com.bmsoft.cloud.sms.strategy;


import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.sms.entity.SmsTask;
import com.bmsoft.cloud.sms.entity.SmsTemplate;

/**
 * 抽象策略类: 发送短信
 * <p>
 * 每个短信 服务商都有自己的 发送短信策略(sdk)
 *
 * @author bmsoft
 * @date 2019-05-15
 */
public interface SmsStrategy {
    /**
     * 发送短信
     *
     * @param task
     * @param template
     * @return
     */
    R<String> sendSms(SmsTask task, SmsTemplate template);
}
