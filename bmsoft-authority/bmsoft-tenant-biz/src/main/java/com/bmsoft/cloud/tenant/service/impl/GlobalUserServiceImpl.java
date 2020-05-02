package com.bmsoft.cloud.tenant.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.bmsoft.cloud.base.service.SuperServiceImpl;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.tenant.dao.GlobalUserMapper;
import com.bmsoft.cloud.tenant.dto.GlobalUserSaveDTO;
import com.bmsoft.cloud.tenant.dto.GlobalUserUpdateDTO;
import com.bmsoft.cloud.tenant.entity.GlobalUser;
import com.bmsoft.cloud.tenant.service.GlobalUserService;
import com.bmsoft.cloud.utils.BeanPlusUtil;
import com.bmsoft.cloud.utils.BizAssert;
import com.bmsoft.cloud.utils.StrHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bmsoft.cloud.utils.BizAssert.isFalse;

/**
 * <p>
 * 业务实现类
 * 全局账号
 * </p>
 *
 * @author bmsoft
 * @date 2019-10-25
 */
@Slf4j
@Service
public class GlobalUserServiceImpl extends SuperServiceImpl<GlobalUserMapper, GlobalUser> implements GlobalUserService {

    @Override
    public Boolean check(String account) {
        return super.count(Wraps.<GlobalUser>lbQ()
                .eq(GlobalUser::getAccount, account)) > 0;
    }

    /**
     * @param data
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GlobalUser save(GlobalUserSaveDTO data) {
        BizAssert.equals(data.getPassword(), data.getConfirmPassword(), "2次输入的密码不一致");
        isFalse(check(data.getAccount()), "账号已经存在");

        String md5Password = SecureUtil.md5(data.getPassword());

        GlobalUser globalAccount = BeanPlusUtil.toBean(data, GlobalUser.class);
        // 全局表就不存用户数据了
        globalAccount.setPassword(md5Password);
        globalAccount.setName(StrHelper.getOrDef(data.getName(), data.getAccount()));
        globalAccount.setReadonly(false);

        save(globalAccount);
        return globalAccount;
    }

    /**
     * @param data
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GlobalUser update(GlobalUserUpdateDTO data) {
        if (StrUtil.isNotBlank(data.getPassword()) || StrUtil.isNotBlank(data.getPassword())) {
            BizAssert.equals(data.getPassword(), data.getConfirmPassword(), "2次输入的密码不一致");
        }

        GlobalUser globalUser = BeanPlusUtil.toBean(data, GlobalUser.class);
        if (StrUtil.isNotBlank(data.getPassword())) {
            String md5Password = SecureUtil.md5(data.getPassword());
            globalUser.setPassword(md5Password);

        }
        updateById(globalUser);
        return globalUser;
    }
}
