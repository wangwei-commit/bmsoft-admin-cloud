package com.bmsoft.cloud.authority.api.hystrix;

import com.bmsoft.cloud.authority.api.UserBizApi;
import com.bmsoft.cloud.base.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户API熔断
 *
 * @author bmsoft
 * @date 2019/07/23
 */
@Component
public class UserBizApiFallback implements UserBizApi {
    @Override
    public R<List<Long>> findAllUserId() {
        return R.timeout();
    }
}
