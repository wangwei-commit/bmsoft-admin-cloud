package com.bmsoft.cloud.work.service.inventory.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.work.dao.inventory.GroupMapper;
import com.bmsoft.cloud.work.entity.inventory.Group;
import com.bmsoft.cloud.work.entity.inventory.GroupParent;
import com.bmsoft.cloud.work.service.inventory.GroupHostService;
import com.bmsoft.cloud.work.service.inventory.GroupParentService;
import com.bmsoft.cloud.work.service.inventory.GroupService;

/**
 * <p>
 * 业务实现类 清单组
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class GroupServiceImpl extends SuperCacheServiceImpl<GroupMapper, Group> implements GroupService {

	@Resource
	private GroupParentService groupParentService;

	@Resource
	private GroupHostService groupHostService;

	@Override
	protected String getRegion() {
		return CacheKey.INVENTORY_GROUP;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		groupParentService.removeByGroupIds(idList);
		groupHostService.removeByGroup(idList);
		return super.removeByIds(idList);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveAndParent(Group group, Long parent) {
		if (parent != null) {
			GroupParent groupParent = GroupParent.builder().toId(parent).fromId(group.getId()).build();
			groupParentService.save(groupParent);
		}
		return super.save(group);
	}

}
