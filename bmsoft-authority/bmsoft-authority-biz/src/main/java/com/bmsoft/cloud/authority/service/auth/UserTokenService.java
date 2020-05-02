package com.bmsoft.cloud.authority.service.auth;

import com.bmsoft.cloud.authority.entity.auth.UserToken;
import com.bmsoft.cloud.base.service.SuperService;

/**
 * <p>
 * 业务接口
 * token
 * </p>
 *
 * @author bmsoft
 * @date 2020-04-02
 */
public interface UserTokenService extends SuperService<UserToken> {
    /**
     * 重置用户登录
     *
     * @return
     */
    boolean reset();
}
