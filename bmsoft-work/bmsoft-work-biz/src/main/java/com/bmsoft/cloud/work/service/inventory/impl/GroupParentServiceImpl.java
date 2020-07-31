package com.bmsoft.cloud.work.service.inventory.impl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.bmsoft.cloud.base.service.SuperServiceImpl;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.work.dao.inventory.GroupParentMapper;
import com.bmsoft.cloud.work.entity.inventory.GroupParent;
import com.bmsoft.cloud.work.service.inventory.GroupParentService;

/**
 * <p>
 * 业务实现类 清单组父子关系
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class GroupParentServiceImpl extends SuperServiceImpl<GroupParentMapper, GroupParent>
		implements GroupParentService {

	@Override
	public boolean removeByGroupIds(Collection<? extends Serializable> idList) {
		return SqlHelper.retBool(baseMapper.delete(Wraps.lbQ(GroupParent.builder().build())
				.in(GroupParent::getFromId, idList).or().in(GroupParent::getToId, idList)));
	}

	@Override
	public boolean removeFromByTo(Serializable toId, Collection<? extends Serializable> fromList) {
		return SqlHelper.retBool(baseMapper.delete(Wraps.lbQ(GroupParent.builder().build())
				.eq(GroupParent::getToId, toId).in(GroupParent::getFromId, fromList)));
	}

}
