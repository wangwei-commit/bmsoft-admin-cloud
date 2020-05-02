package com.bmsoft.cloud.oauth.granter;

import com.bmsoft.cloud.authority.dto.auth.LoginParamDTO;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.jwt.model.AuthInfo;
import org.springframework.stereotype.Component;

import static com.bmsoft.cloud.oauth.granter.PasswordTokenGranter.GRANT_TYPE;

/**
 * 账号密码登录获取token
 *
 * @author bmsoft
 * @date 2020年03月31日10:22:55
 */
@Component(GRANT_TYPE)
public class PasswordTokenGranter extends AbstractTokenGranter implements TokenGranter {

    public static final String GRANT_TYPE = "password";

    @Override
    public R<AuthInfo> grant(LoginParamDTO tokenParameter) {
        return login(tokenParameter);
    }
}
