package com.bmsoft.cloud.work.service.inventory.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.bmsoft.cloud.base.service.SuperServiceImpl;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.work.dao.inventory.GroupHostMapper;
import com.bmsoft.cloud.work.entity.inventory.GroupHost;
import com.bmsoft.cloud.work.service.inventory.GroupHostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 业务实现类 清单组与主机关系
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class GroupHostServiceImpl extends SuperServiceImpl<GroupHostMapper, GroupHost> implements GroupHostService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByHost(Collection<? extends Serializable> idList) {
		return SqlHelper
				.retBool(baseMapper.delete(Wraps.lbQ(GroupHost.builder().build()).in(GroupHost::getHostId, idList)));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByGroup(Collection<? extends Serializable> idList) {
		return SqlHelper
				.retBool(baseMapper.delete(Wraps.lbQ(GroupHost.builder().build()).in(GroupHost::getGroupId, idList)));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeGroupByHost(Serializable hostId, Collection<? extends Serializable> groupList) {
		return SqlHelper.retBool(baseMapper.delete(Wraps.lbQ(GroupHost.builder().build())
				.eq(GroupHost::getHostId, hostId).in(GroupHost::getGroupId, groupList)));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeHostByGroup(Serializable groupId, Collection<? extends Serializable> hostList) {
		return SqlHelper.retBool(baseMapper.delete(Wraps.lbQ(GroupHost.builder().build())
				.eq(GroupHost::getGroupId, groupId).in(GroupHost::getHostId, hostList)));
	}

}
