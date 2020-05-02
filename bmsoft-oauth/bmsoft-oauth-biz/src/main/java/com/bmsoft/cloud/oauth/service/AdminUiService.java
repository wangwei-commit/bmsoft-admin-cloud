package com.bmsoft.cloud.oauth.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.boot.utils.WebUtils;
import com.bmsoft.cloud.common.constant.BizConstant;
import com.bmsoft.cloud.exception.BizException;
import com.bmsoft.cloud.exception.code.ExceptionCode;
import com.bmsoft.cloud.jwt.TokenUtil;
import com.bmsoft.cloud.jwt.model.AuthInfo;
import com.bmsoft.cloud.jwt.model.JwtUserInfo;
import com.bmsoft.cloud.jwt.utils.JwtUtil;
import com.bmsoft.cloud.tenant.entity.GlobalUser;
import com.bmsoft.cloud.tenant.service.GlobalUserService;
import com.bmsoft.cloud.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bmsoft.cloud.context.BaseContextConstants.BASIC_HEADER_KEY;

/**
 * @author bmsoft
 * @createTime 2017-12-15 13:42
 */
@Service
@Slf4j
public class AdminUiService {

    @Autowired
    protected TokenUtil tokenUtil;
    @Autowired
    private GlobalUserService globalUserService;

    /**
     * 超管账号登录
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    public R<AuthInfo> adminLogin(String account, String password) {
        String basicHeader = ServletUtil.getHeader(WebUtils.request(), BASIC_HEADER_KEY, StrPool.UTF_8);
        String[] client = JwtUtil.getClient(basicHeader);

        GlobalUser user = this.globalUserService.getOne(Wrappers.<GlobalUser>lambdaQuery()
                .eq(GlobalUser::getAccount, account).eq(GlobalUser::getTenantCode, BizConstant.SUPER_TENANT));
        // 密码错误
        if (user == null) {
            throw new BizException(ExceptionCode.JWT_USER_INVALID.getCode(), ExceptionCode.JWT_USER_INVALID.getMsg());
        }

        String passwordMd5 = SecureUtil.md5(password);
        if (!user.getPassword().equalsIgnoreCase(passwordMd5)) {
            return R.fail("用户名或密码错误!");
        }
        JwtUserInfo userInfo = new JwtUserInfo(user.getId(), user.getAccount(), user.getName());

        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo, null);
        log.info("token={}", authInfo.getToken());
        return R.success(authInfo);
    }


}
