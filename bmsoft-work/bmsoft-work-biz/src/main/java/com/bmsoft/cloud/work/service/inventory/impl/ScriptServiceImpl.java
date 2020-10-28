package com.bmsoft.cloud.work.service.inventory.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.work.dao.inventory.ScriptMapper;
import com.bmsoft.cloud.work.entity.inventory.Script;
import com.bmsoft.cloud.work.service.inventory.ScriptService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类 清单脚本
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Service
public class ScriptServiceImpl extends SuperCacheServiceImpl<ScriptMapper, Script> implements ScriptService {

	@Override
	protected String getRegion() {
		return CacheKey.INVENTORY_SCRIPT;
	}

	@Override
	@InjectionResult
	public <E extends IPage<Script>> E page(E page, Wrapper<Script> queryWrapper) {
		return super.page(page, queryWrapper);
	}
}
