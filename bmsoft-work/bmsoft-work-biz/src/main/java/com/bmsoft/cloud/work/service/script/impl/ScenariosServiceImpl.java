package com.bmsoft.cloud.work.service.script.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.work.dao.script.ScenariosMapper;
import com.bmsoft.cloud.work.entity.scripts.Scenarios;
import com.bmsoft.cloud.work.properties.TypeProperties;
import com.bmsoft.cloud.work.service.script.ScenariosService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 业务实现类 运维场景
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
@Service
public class ScenariosServiceImpl extends SuperCacheServiceImpl<ScenariosMapper, Scenarios>
		implements ScenariosService {
	@Resource
	private TypeProperties typeProperties;

	@Override
	protected String getRegion() {
		return CacheKey.SCENARIOS;
	}



	@Override
	@InjectionResult
	public <E extends IPage<Scenarios>> E page(E page, Wrapper<Scenarios> queryWrapper) {
		return super.page(page, queryWrapper);
	}

	@Override
	@InjectionResult
	public Scenarios getByIdCache(Serializable id) {
		return super.getByIdCache(id);
	}


	@Override
	public List<TypeProperties.PlateForm> getTypeList() {
		return typeProperties.getPlatformSource();
	}


}
