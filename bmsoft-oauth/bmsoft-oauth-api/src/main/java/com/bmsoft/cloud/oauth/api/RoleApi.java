package com.bmsoft.cloud.oauth.api;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.oauth.api.hystrix.RoleApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 角色API
 *
 * @author bmsoft
 * @date 2019/08/02
 */
@FeignClient(name = "${bmsoft.feign.oauth-server:bmsoft-oauth-server}", path = "/role", fallback = RoleApiFallback.class)
public interface RoleApi {
    /**
     * 根据角色编码，查找用户id
     *
     * @param codes 角色编码
     * @return
     */
    @GetMapping("/codes")
    R<List<Long>> findUserIdByCode(@RequestParam(value = "codes") String[] codes);
}
