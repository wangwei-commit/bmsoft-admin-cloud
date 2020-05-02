package com.bmsoft.cloud.oauth.api.hystrix;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.log.entity.OptLogDTO;
import com.bmsoft.cloud.oauth.api.LogApi;
import org.springframework.stereotype.Component;

/**
 * 日志操作 熔断
 *
 * @author bmsoft
 * @date 2019/07/02
 */
@Component
public class LogApiHystrix implements LogApi {
    @Override
    public R<OptLogDTO> save(OptLogDTO log) {
        return R.timeout();
    }
}
