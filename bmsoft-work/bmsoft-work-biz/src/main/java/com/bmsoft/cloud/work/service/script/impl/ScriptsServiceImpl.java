package com.bmsoft.cloud.work.service.script.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.work.dao.script.ScriptsMapper;
import com.bmsoft.cloud.work.entity.scripts.Scripts;
import com.bmsoft.cloud.work.service.script.ScriptsService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 业务实现类 运维场景
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
@Service
public class ScriptsServiceImpl extends SuperCacheServiceImpl<ScriptsMapper, Scripts>
		implements ScriptsService {

	@Override
	protected String getRegion() {
		return CacheKey.SCRIPTS;
	}



	@Override
	@InjectionResult
	public <E extends IPage<Scripts>> E page(E page, Wrapper<Scripts> queryWrapper) {
		return super.page(page, queryWrapper);
	}

	@Override
	@InjectionResult
	public Scripts getByIdCache(Serializable id) {
		return super.getByIdCache(id);
	}





}
