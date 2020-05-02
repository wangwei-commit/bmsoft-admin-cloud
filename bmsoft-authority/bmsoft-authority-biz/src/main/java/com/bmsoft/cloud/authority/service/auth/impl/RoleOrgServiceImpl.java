package com.bmsoft.cloud.authority.service.auth.impl;


import com.bmsoft.cloud.authority.dao.auth.RoleOrgMapper;
import com.bmsoft.cloud.authority.entity.auth.RoleOrg;
import com.bmsoft.cloud.authority.service.auth.RoleOrgService;
import com.bmsoft.cloud.base.service.SuperServiceImpl;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 角色组织关系
 * </p>
 *
 * @author bmsoft
 * @date 2019-07-03
 */
@Slf4j
@Service

public class RoleOrgServiceImpl extends SuperServiceImpl<RoleOrgMapper, RoleOrg> implements RoleOrgService {
    @Override
    public List<Long> listOrgByRoleId(Long id) {
        List<RoleOrg> list = super.list(Wraps.<RoleOrg>lbQ().eq(RoleOrg::getRoleId, id));
        List<Long> orgList = list.stream().mapToLong(RoleOrg::getOrgId).boxed().collect(Collectors.toList());
        return orgList;
    }
}
