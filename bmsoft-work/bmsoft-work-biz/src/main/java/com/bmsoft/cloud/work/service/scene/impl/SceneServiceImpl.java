package com.bmsoft.cloud.work.service.scene.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.service.SuperCacheServiceImpl;
import com.bmsoft.cloud.common.constant.CacheKey;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.LbqWrapper;
import com.bmsoft.cloud.injection.annonation.InjectionResult;
import com.bmsoft.cloud.utils.MapHelper;
import com.bmsoft.cloud.work.dao.scene.SceneMapper;
import com.bmsoft.cloud.work.entity.scene.Scene;
import com.bmsoft.cloud.work.entity.scripts.Scenarios;
import com.bmsoft.cloud.work.entity.scripts.Scripts;
import com.bmsoft.cloud.work.service.scene.SceneService;
import com.bmsoft.cloud.work.service.script.ScenariosService;
import com.bmsoft.cloud.work.service.script.ScriptsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
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
public class SceneServiceImpl extends SuperCacheServiceImpl<SceneMapper, Scene>
		implements SceneService {

	@Resource
	private ScenariosService scenariosService;
	@Resource
	private ScriptsService scriptsService;
	@Override
	protected String getRegion() {
		return CacheKey.SCENE;
	}



	@Override
	@InjectionResult
	public <E extends IPage<Scene>> E page(E page, Wrapper<Scene> queryWrapper) {
		return super.page(page, queryWrapper);
	}

	@Override
	@InjectionResult
	public Scene getByIdCache(Serializable id) {
		return super.getByIdCache(id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		List<Scene> scenes= super.listByIds(idList);
		idList = scenes.stream().filter(scene -> !scene.getIsDefault()).map(Scene::getId).collect(Collectors.toList());
		return super.removeByIds(idList);
	}




	@Override
	public List<Scene> findScenesByKey(String key) {
		LbqWrapper<Scenarios> query = Wraps.<Scenarios>lbQ().like(Scenarios::getName, key);
		List<Scenarios> list = scenariosService.list(query);
		Map<Long, Long> collect = list.stream().collect(
				Collectors.groupingBy(scenarios ->scenarios.getScene().getKey(),Collectors.counting()));
		LbqWrapper<Scripts> scriptsQuery = Wraps.<Scripts>lbQ().like(Scripts::getName, key);
		List<Scripts> scriptsList = scriptsService.list(scriptsQuery);
		Map<Long, Long> scriptsCollect = scriptsList.stream().collect(
				Collectors.groupingBy(scripts ->scripts.getScene().getKey(),Collectors.counting()));
		LbqWrapper<Scene> sceneQuery = Wraps.<Scene>lbQ().orderByDesc(Scene::getCreateTime);
		List<Scene> scenes =  super.list(sceneQuery);

		scenes.stream().forEach(scene ->{
			 if( collect.containsKey(scene.getId())){
				 scene.setScenariosCount(collect.get(scene.getId()));
			 } else {
				 scene.setScenariosCount(0L);
			 }
			 if( scriptsCollect.containsKey(scene.getId())){
				 scene.setScriptsCount(scriptsCollect.get(scene.getId()));
			 }else {
				 scene.setScriptsCount(0L);
			 }
		});

		return scenes;
	}

	@Override
	public Map<Serializable, Object> findNameByIds(Set<Serializable> ids) {
		List<Scene> list = getScenes(ids);
		return MapHelper.uniqueIndex(list, Scene::getId, Scene::getName);
	}

	private List<Scene> getScenes(Set<Serializable> ids) {
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<Long> idList = ids.stream().mapToLong(Convert::toLong).boxed().collect(Collectors.toList());
		List<Scene> list = null;
		if (idList.size() <= 1000) {
			list = idList.stream().map(this::getByIdCache).filter(Objects::nonNull).collect(Collectors.toList());
		} else {
			LbqWrapper<Scene> query = Wraps.<Scene>lbQ().in(Scene::getId, idList);
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
