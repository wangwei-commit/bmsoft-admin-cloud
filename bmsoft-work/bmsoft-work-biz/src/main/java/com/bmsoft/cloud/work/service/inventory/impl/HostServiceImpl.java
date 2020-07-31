package com.bmsoft.cloud.work.service.inventory.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.work.dao.inventory.HostMapper;
import com.bmsoft.cloud.work.entity.inventory.Host;
import com.bmsoft.cloud.work.service.inventory.GroupHostService;
import com.bmsoft.cloud.work.service.inventory.HostService;

/**
 * <p>
 * 业务实现类 清单主机
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class HostServiceImpl extends SuperCacheServiceImpl<HostMapper, Host> implements HostService {

	@Resource
	private GroupHostService groupHostService;
	
	@Override
	protected String getRegion() {
		return CacheKey.INVENTORY_HOST;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		groupHostService.removeByHost(idList);
		return super.removeByIds(idList);
	}
	
	@Override
	@InjectionResult
	public <E extends IPage<Host>> E page(E page, Wrapper<Host> queryWrapper) {
		return super.page(page, queryWrapper);
	}
}
