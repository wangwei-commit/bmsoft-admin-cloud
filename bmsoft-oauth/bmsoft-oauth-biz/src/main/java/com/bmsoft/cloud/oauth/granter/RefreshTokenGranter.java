package com.bmsoft.cloud.oauth.granter;

import com.bmsoft.cloud.authority.dto.auth.LoginParamDTO;
import com.bmsoft.cloud.authority.entity.auth.User;
import com.bmsoft.cloud.authority.event.LoginEvent;
import com.bmsoft.cloud.authority.event.model.LoginStatusDTO;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.context.BaseContextConstants;
import com.bmsoft.cloud.jwt.model.AuthInfo;
import com.bmsoft.cloud.utils.SpringUtils;
import com.bmsoft.cloud.utils.StrHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.bmsoft.cloud.oauth.granter.RefreshTokenGranter.GRANT_TYPE;

/**
 * RefreshTokenGranter
 *
 * @author bmsoft
 * @date 2020年03月31日10:23:53
 */
@Component(GRANT_TYPE)
public class RefreshTokenGranter extends AbstractTokenGranter implements TokenGranter {

    public static final String GRANT_TYPE = "refresh_token";

    @Override
    public R<AuthInfo> grant(LoginParamDTO loginParam) {
        String grantType = loginParam.getGrantType();
        String refreshTokenStr = loginParam.getRefreshToken();
        if (StrHelper.isAnyBlank(grantType, refreshTokenStr) || !GRANT_TYPE.equals(grantType)) {
            return R.fail("加载用户信息失败");
        }

        AuthInfo authInfo = tokenUtil.parseJWT(refreshTokenStr);

        if (!BaseContextConstants.REFRESH_TOKEN_KEY.equals(authInfo.getTokenType())) {
            return R.fail("refreshToken无效，无法加载用户信息");
        }

        User user = userService.getByIdCache(authInfo.getUserId());

        if (user == null || !user.getStatus()) {
            String msg = "您已被禁用！";
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
            return R.fail(msg);
        }

        // 密码过期
        if (user.getPasswordExpireTime() != null && LocalDateTime.now().isAfter(user.getPasswordExpireTime())) {
            String msg = "用户密码已过期，请修改密码或者联系管理员重置!";
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
            return R.fail(msg);
        }

        return R.success(createToken(user));

    }
}
