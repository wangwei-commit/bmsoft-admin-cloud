package com.bmsoft.cloud.oauth.api.hystrix;

import com.bmsoft.cloud.oauth.api.UserApi;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户API熔断
 *
 * @author bmsoft
 * @date 2019/07/23
 */
@Component
public class UserApiFallback implements UserApi {
    @Override
    public Map<String, Object> getDataScopeById(Long id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("dsType", 5);
        map.put("orgIds", Collections.emptyList());
        return map;
    }

    @Override
    public Map<Serializable, Object> findUserByIds(Set<Serializable> codes) {
        return Collections.emptyMap();
    }

    @Override
    public Map<Serializable, Object> findUserNameByIds(Set<Serializable> codes) {
        return Collections.emptyMap();
    }

	@Override
	public List<Long> getUserOrgIdChildById(Long id) {
		return Collections.emptyList();
	}
}
