package com.bmsoft.cloud.authority.dao.auth;

import com.bmsoft.cloud.authority.entity.auth.UserRole;
import com.bmsoft.cloud.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 角色分配
 * 账号角色绑定
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-03
 */
@Repository
public interface UserRoleMapper extends SuperMapper<UserRole> {

}
