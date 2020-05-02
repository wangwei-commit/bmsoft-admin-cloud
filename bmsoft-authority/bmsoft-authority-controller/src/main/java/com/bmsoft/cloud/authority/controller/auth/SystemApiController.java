package com.bmsoft.cloud.authority.controller.auth;


import cn.hutool.core.util.StrUtil;
import com.bmsoft.cloud.authority.dto.auth.SystemApiSaveDTO;
import com.bmsoft.cloud.authority.dto.auth.SystemApiUpdateDTO;
import com.bmsoft.cloud.authority.entity.auth.SystemApi;
import com.bmsoft.cloud.authority.service.auth.SystemApiService;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.utils.BeanPlusUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * API接口
 * </p>
 *
 * @author bmsoft
 * @date 2019-12-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/systemApi")
@Api(value = "SystemApi", tags = "API接口")
@PreAuth(replace = "systemApi:")
public class SystemApiController extends SuperCacheController<SystemApiService, Long, SystemApi, SystemApi, SystemApiSaveDTO, SystemApiUpdateDTO> {

    @Override
    public R<SystemApi> handlerSave(SystemApiSaveDTO data) {
        SystemApi systemApi = BeanPlusUtil.toBean(data, SystemApi.class);
        systemApi.setIsPersist(false);
        if (StrUtil.isEmpty(systemApi.getCode())) {
            systemApi.setCode(DigestUtils.md5Hex(systemApi.getServiceId() + systemApi.getPath()));
        }

        baseService.save(systemApi);
        return success(systemApi);
    }

}
