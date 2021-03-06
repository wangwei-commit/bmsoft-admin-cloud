package com.bmsoft.cloud.oauth.api.hystrix;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.oauth.api.ParameterApi;
import org.springframework.stereotype.Component;


/**
 * 熔断类
 *
 * @author bmsoft
 * @date 2020年02月09日11:24:23
 */
@Component
public class ParameterApiFallback implements ParameterApi {
    @Override
    public R<String> getValue(String key, String defVal) {
        return R.timeout();
    }
}
