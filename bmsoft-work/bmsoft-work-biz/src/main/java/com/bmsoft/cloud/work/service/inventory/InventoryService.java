package com.bmsoft.cloud.work.service.inventory;

import com.bmsoft.cloud.base.service.SuperCacheService;
import com.bmsoft.cloud.work.entity.inventory.Inventory;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 业务接口 清单
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
public interface InventoryService extends SuperCacheService<Inventory> {

	Map<Serializable, Object> findNameByIds(Set<Serializable> ids);
}
