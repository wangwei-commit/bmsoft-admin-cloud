package com.bmsoft.cloud.oauth.granter;

import com.bmsoft.cloud.authority.dto.auth.LoginParamDTO;
import com.bmsoft.cloud.authority.event.LoginEvent;
import com.bmsoft.cloud.authority.event.model.LoginStatusDTO;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.context.BaseContextHandler;
import com.bmsoft.cloud.exception.BizException;
import com.bmsoft.cloud.jwt.model.AuthInfo;
import com.bmsoft.cloud.oauth.service.ValidateCodeService;
import com.bmsoft.cloud.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bmsoft.cloud.oauth.granter.CaptchaTokenGranter.GRANT_TYPE;

/**
 * 验证码TokenGranter
 *
 * @author Chill
 */
@Component(GRANT_TYPE)
@Slf4j
public class CaptchaTokenGranter extends AbstractTokenGranter implements TokenGranter {

    public static final String GRANT_TYPE = "captcha";
    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public R<AuthInfo> grant(LoginParamDTO loginParam) {
        R<Boolean> check = validateCodeService.check(loginParam.getKey(), loginParam.getCode());
        if (check.getIsError()) {
            String msg = check.getMsg();
            BaseContextHandler.setTenant(loginParam.getTenant());
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(loginParam.getAccount(), msg)));
            throw BizException.validFail(check.getMsg());
        }

        return login(loginParam);
    }

}
