package com.bmsoft.cloud.authority.api;

import com.bmsoft.cloud.authority.api.hystrix.UserBizApiFallback;
import com.bmsoft.cloud.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 用户
 *
 * @author bmsoft
 * @date 2019/07/02
 */
@FeignClient(name = "${bmsoft.feign.authority-server:bmsoft-authority-server}", fallback = UserBizApiFallback.class
        , path = "/user", qualifier = "userBizApi")
public interface UserBizApi {

    /**
     * 查询所有的用户id
     *
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    R<List<Long>> findAllUserId();
}
