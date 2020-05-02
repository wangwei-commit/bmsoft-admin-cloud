package com.bmsoft.cloud.oauth.api.hystrix;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.oauth.api.RoleApi;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色查询API
 *
 * @author bmsoft
 * @date 2019/08/02
 */
@Component
public class RoleApiFallback implements RoleApi {
    @Override
    public R<List<Long>> findUserIdByCode(String[] codes) {
        return R.timeout();
    }
}
