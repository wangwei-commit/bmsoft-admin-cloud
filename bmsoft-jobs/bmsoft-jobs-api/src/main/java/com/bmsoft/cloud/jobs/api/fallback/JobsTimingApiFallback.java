package com.bmsoft.cloud.jobs.api.fallback;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.jobs.api.JobsTimingApi;
import com.bmsoft.cloud.jobs.dto.XxlJobInfo;
import org.springframework.stereotype.Component;

/**
 * 定时API 熔断
 *
 * @author bmsoft
 * @date 2019/07/16
 */
@Component
public class JobsTimingApiFallback implements JobsTimingApi {
    @Override
    public R<String> addTimingTask(XxlJobInfo xxlJobInfo) {
        return R.timeout();
    }
}
