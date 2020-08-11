package com.bmsoft.cloud.work.service.inventory.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.LbqWrapper;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.model.RemoteData;
import com.bmsoft.cloud.utils.MapHelper;
import com.bmsoft.cloud.work.dao.inventory.InventoryMapper;
import com.bmsoft.cloud.work.entity.inventory.Group;
import com.bmsoft.cloud.work.entity.inventory.Host;
import com.bmsoft.cloud.work.entity.inventory.Inventory;
import com.bmsoft.cloud.work.entity.inventory.Source;
import com.bmsoft.cloud.work.enumeration.inventory.SynStatus;
import com.bmsoft.cloud.work.service.inventory.GroupService;
import com.bmsoft.cloud.work.service.inventory.HostService;
import com.bmsoft.cloud.work.service.inventory.InventoryService;
import com.bmsoft.cloud.work.service.inventory.SourceService;

import cn.hutool.core.convert.Convert;

/**
 * <p>
 * 业务实现类 清单
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class InventoryServiceImpl extends SuperCacheServiceImpl<InventoryMapper, Inventory>
		implements InventoryService {

	@Resource
	private GroupService groupService;

	@Resource
	private HostService hostService;

	@Resource
	private SourceService sourceService;

	@Override
	protected String getRegion() {
		return CacheKey.INVENTORY;
	}

	@Override
	protected R<Boolean> handlerSave(Inventory model) {
		model.setSynStatus(SynStatus.UNKNOWN);
		return super.handlerSave(model);
	}

	@Override
	@InjectionResult
	public <E extends IPage<Inventory>> E page(E page, Wrapper<Inventory> queryWrapper) {
		return super.page(page, queryWrapper);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		List<RemoteData> inventoryIdList = idList.stream().map(id -> new RemoteData(id)).collect(Collectors.toList());
		groupService.removeByIds(
				groupService.list(Wraps.lbQ(Group.builder().build()).in(Group::getInventory, inventoryIdList)).stream()
						.map(Group::getId).collect(Collectors.toList()));
		hostService
				.removeByIds(hostService.list(Wraps.lbQ(Host.builder().build()).in(Host::getInventory, inventoryIdList))
						.stream().map(Host::getId).collect(Collectors.toList()));
		sourceService.removeByIds(
				sourceService.list(Wraps.lbQ(Source.builder().build()).in(Source::getInventory, inventoryIdList))
						.stream().map(Source::getId).collect(Collectors.toList()));
		return super.removeByIds(idList);
	}

	@Override
	public Map<Serializable, Object> findNameByIds(Set<Serializable> ids) {
		List<Inventory> list = getInventorys(ids);
		return MapHelper.uniqueIndex(list, Inventory::getId, Inventory::getName);
	}

	private List<Inventory> getInventorys(Set<Serializable> ids) {
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<Long> idList = ids.stream().mapToLong(Convert::toLong).boxed().collect(Collectors.toList());
		List<Inventory> list = null;
		if (idList.size() <= 1000) {
			list = idList.stream().map(this::getByIdCache).filter(Objects::nonNull).collect(Collectors.toList());
		} else {
			LbqWrapper<Inventory> query = Wraps.<Inventory>lbQ().in(Inventory::getId, idList);
			list = super.list(query);
			if (!list.isEmpty()) {
				list.forEach(item -> {
					String itemKey = key(item.getId());
					cacheChannel.set(getRegion(), itemKey, item);
				});
			}
		}
		return list;
	}

}
