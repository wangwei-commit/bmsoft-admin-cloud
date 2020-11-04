package com.bmsoft.cloud.work.service.template.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.work.dao.template.TemplateMapper;
import com.bmsoft.cloud.work.entity.scripts.Scenarios;
import com.bmsoft.cloud.work.entity.scripts.Scripts;
import com.bmsoft.cloud.work.entity.template.Template;
import com.bmsoft.cloud.work.service.script.ScenariosService;
import com.bmsoft.cloud.work.service.script.ScriptsService;
import com.bmsoft.cloud.work.service.template.TemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类 运维场景
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
@Service
public class TemplateServiceImpl extends SuperCacheServiceImpl<TemplateMapper, Template>
		implements TemplateService {
	@Resource
	private ScenariosService scenariosService;

	@Resource
	private ScriptsService scriptsService;

	@Override
	protected String getRegion() {
		return CacheKey.TEMPLATE;
	}



	@Override
	@InjectionResult
	public <E extends IPage<Template>> E page(E page, Wrapper<Template> queryWrapper) {
		return super.page(page, queryWrapper);
	}

	@Override
	@InjectionResult
	public Template getByIdCache(Serializable id) {
		return super.getByIdCache(id);
	}


	@Override
	public Map<Serializable, Object> findNameByIds(Set<Serializable> ids) {
		List<Scenarios> scenariosList = scenariosService.listByIds(ids);
		if (!scenariosList.isEmpty()){
			return scenariosList.stream().collect(Collectors.toMap(scenarios -> scenarios.getId(), scenarios -> scenarios.getName()));
		}
		List<Scripts> scriptsList = scriptsService.listByIds(ids);
		if (!scriptsList.isEmpty()){
			return scriptsList.stream().collect(Collectors.toMap(scripts -> scripts.getId(),scripts -> scripts.getName()));
		}
		return null;
	}
}
