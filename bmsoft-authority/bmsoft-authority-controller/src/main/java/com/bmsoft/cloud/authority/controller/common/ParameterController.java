package com.bmsoft.cloud.authority.controller.common;


import com.bmsoft.cloud.authority.dto.common.ParameterPageDTO;
import com.bmsoft.cloud.authority.dto.common.ParameterSaveDTO;
import com.bmsoft.cloud.authority.dto.common.ParameterUpdateDTO;
import com.bmsoft.cloud.authority.entity.common.Parameter;
import com.bmsoft.cloud.authority.service.common.ParameterService;
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
 * 参数配置
 * </p>
 *
 * @author bmsoft
 * @date 2020-02-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/parameter")
@Api(value = "Parameter", tags = "参数配置")
@PreAuth(replace = "parameter:")
public class ParameterController extends SuperController<ParameterService, Long, Parameter, ParameterPageDTO, ParameterSaveDTO, ParameterUpdateDTO> {

}
