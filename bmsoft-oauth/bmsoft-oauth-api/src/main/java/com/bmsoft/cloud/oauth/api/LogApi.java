package com.bmsoft.cloud.oauth.api;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.log.entity.OptLogDTO;
import com.bmsoft.cloud.oauth.api.hystrix.LogApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 操作日志保存 API
 *
 * @author bmsoft
 * @date 2019/07/02
 */
@FeignClient(name = "${bmsoft.feign.oauth-server:bmsoft-oauth-server}", fallback = LogApiHystrix.class, qualifier = "logApi")
public interface LogApi {

    /**
     * 保存日志
     *
     * @param log 日志
     * @return
     */
    @RequestMapping(value = "/optLog", method = RequestMethod.POST)
    R<OptLogDTO> save(@RequestBody OptLogDTO log);

}
