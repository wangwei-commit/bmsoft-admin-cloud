package com.bmsoft.cloud.work.service.scene;

import com.alibaba.fastjson.JSONObject;
import com.bmsoft.cloud.base.service.SuperCacheService;
import com.bmsoft.cloud.work.entity.scene.Scene;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 业务接口 运维场景
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
public interface SceneService extends SuperCacheService<Scene> {

	/**
	 * 根据脚本、剧本名称查找场景
	 * @param key
	 * @return
	 */
	Map<Scene, JSONObject> findScenesByKey(String key);
	/**
	 * 根据ID查找场景
	 * @param ids
	 * @return
	 */
	Map<Serializable, Object> findNameByIds(Set<Serializable> ids);
}
