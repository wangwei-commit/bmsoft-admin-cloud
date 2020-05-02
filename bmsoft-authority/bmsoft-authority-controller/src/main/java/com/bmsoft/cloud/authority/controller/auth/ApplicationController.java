package com.bmsoft.cloud.authority.controller.auth;


import cn.hutool.core.util.RandomUtil;
import com.bmsoft.cloud.authority.dto.auth.ApplicationPageDTO;
import com.bmsoft.cloud.authority.dto.auth.ApplicationSaveDTO;
import com.bmsoft.cloud.authority.dto.auth.ApplicationUpdateDTO;
import com.bmsoft.cloud.authority.entity.auth.Application;
import com.bmsoft.cloud.authority.service.auth.ApplicationService;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperController;
import com.bmsoft.cloud.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 应用
 * </p>
 *
 * @author bmsoft
 * @date 2019-12-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/application")
@Api(value = "Application", tags = "应用")
@PreAuth(replace = "application:")
public class ApplicationController extends SuperController<ApplicationService, Long, Application, ApplicationPageDTO, ApplicationSaveDTO, ApplicationUpdateDTO> {

    @Override
    public R<Application> handlerSave(ApplicationSaveDTO applicationSaveDTO) {
        applicationSaveDTO.setClientId(RandomUtil.randomString(24));
        applicationSaveDTO.setClientSecret(RandomUtil.randomString(32));
        return super.handlerSave(applicationSaveDTO);
    }

}
