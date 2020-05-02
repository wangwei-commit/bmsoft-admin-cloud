package com.bmsoft.cloud.oauth.granter;


import com.bmsoft.cloud.authority.dto.auth.LoginParamDTO;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.jwt.model.AuthInfo;

/**
 * 授权认证统一接口.
 *
 * @author bmsoft
 * @date 2020年03月31日10:21:21
 */
public interface TokenGranter {

    /**
     * 获取用户信息
     *
     * @param loginParam 授权参数
     * @return LoginDTO
     */
    R<AuthInfo> grant(LoginParamDTO loginParam);

}
