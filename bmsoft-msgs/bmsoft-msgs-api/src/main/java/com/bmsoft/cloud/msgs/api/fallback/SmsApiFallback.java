package com.bmsoft.cloud.msgs.api.fallback;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.msgs.api.SmsApi;
import com.bmsoft.cloud.sms.dto.SmsSendTaskDTO;
import com.bmsoft.cloud.sms.dto.VerificationCodeDTO;
import com.bmsoft.cloud.sms.entity.SmsTask;
import org.springframework.stereotype.Component;

/**
 * 熔断
 *
 * @author bmsoft
 * @date 2019/07/25
 */
@Component
public class SmsApiFallback implements SmsApi {
    @Override
    public R<SmsTask> send(SmsSendTaskDTO smsTaskDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> sendCode(VerificationCodeDTO data) {
        return R.timeout();
    }

    @Override
    public R<Boolean> verification(VerificationCodeDTO data) {
        return R.timeout();
    }
}
