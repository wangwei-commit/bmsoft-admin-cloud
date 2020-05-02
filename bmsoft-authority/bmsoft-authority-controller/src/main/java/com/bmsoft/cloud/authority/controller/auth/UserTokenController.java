package com.bmsoft.cloud.authority.controller.auth;

import com.bmsoft.cloud.authority.dto.auth.UserTokenPageDTO;
import com.bmsoft.cloud.authority.dto.auth.UserTokenSaveDTO;
import com.bmsoft.cloud.authority.dto.auth.UserTokenUpdateDTO;
import com.bmsoft.cloud.authority.entity.auth.UserToken;
import com.bmsoft.cloud.authority.service.auth.UserTokenService;
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
 * token
 * </p>
 *
 * @author bmsoft
 * @date 2020-04-02
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/userToken")
@Api(value = "UserToken", tags = "token")
@PreAuth(replace = "userToken:")
public class UserTokenController extends SuperController<UserTokenService, Long, UserToken, UserTokenPageDTO, UserTokenSaveDTO, UserTokenUpdateDTO> {


}
