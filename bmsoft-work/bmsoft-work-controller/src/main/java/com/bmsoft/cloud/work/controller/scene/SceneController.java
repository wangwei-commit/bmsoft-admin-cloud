package com.bmsoft.cloud.work.controller.scene;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.LbqWrapper;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.log.annotation.SysLog;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.dto.scene.ScenePageDTO;
import com.bmsoft.cloud.work.dto.scene.SceneSaveDTO;
import com.bmsoft.cloud.work.dto.scene.SceneUpdateDTO;
import com.bmsoft.cloud.work.entity.scene.Scene;
import com.bmsoft.cloud.work.entity.scripts.Scenarios;
import com.bmsoft.cloud.work.entity.scripts.Scripts;
import com.bmsoft.cloud.work.service.scene.SceneService;
import com.bmsoft.cloud.work.service.script.ScenariosService;
import com.bmsoft.cloud.work.service.script.ScriptsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

;

/**
 * <p>
 * 前端控制器 运维场景
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
@Validated
@RestController
@RequestMapping("/scene")
@Api(value = "Scene", tags = "运维场景")
@PreAuth(replace = "scene:")
public class SceneController extends
		SuperCacheController<SceneService, Long, Scene, ScenePageDTO, SceneSaveDTO, SceneUpdateDTO> {

	@Resource

	public SceneService sceneService;

	@Resource

	public ScenariosService scenariosService;
	@Resource

	public ScriptsService scriptsService;

	@SuppressWarnings("unchecked")
	@Override
	public R<Scene> handlerSave(SceneSaveDTO model) {
		model.setIsDefault(false);
		return   super.handlerSave(model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Scene> handlerUpdate(SceneUpdateDTO model) {

		if (model.getIsDefault()) {
			return R.fail(400, "内置场景不可修改");
		}
		return  super.handlerUpdate(model);
	}

	@Override
	public R<Boolean> handlerDelete(List<Long> longs) {
		List<Scene> list = sceneService.listByIds(longs);

		if (list.stream().anyMatch(scene -> scene.getIsDefault())) {
			return R.fail(400, "存在内置场景，不可删除");
		}
		LbqWrapper<Scenarios> query = Wraps.<Scenarios>lbQ().in(Scenarios::getScene, longs);
		List<Scenarios> scenarios = scenariosService.list(query);
		if(scenarios!=null&&!scenarios.isEmpty()){
			return R.fail(400, "存在关联脚本，不可删除");
		}
		LbqWrapper<Scripts> scriptsQuery = Wraps.<Scripts>lbQ().in(Scripts::getScene, longs);
		List<Scripts> scriptsList = scriptsService.list(scriptsQuery);
		if(scriptsList!=null&&!scriptsList.isEmpty()){
			return R.fail(400, "存在关联剧本，不可删除");
		}
		return  super.handlerDelete(longs);
	}

	@Override
	public void handlerWrapper(QueryWrap<Scene> wrapper, PageParams<ScenePageDTO> params) {
//		currentUserOperate.setQueryWrapByOrg(wrapper, getDbField("org", getEntityClass()));
		super.handlerWrapper(wrapper, params);
	}

	@Override
	public void handlerResult(IPage<Scene> page) {
		super.handlerResult(page);
	}


	@ApiOperation(value = "关键字查找场景")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "key", value = "脚本名或剧本名", dataType = "String", paramType = "query")})
	@PostMapping("/searchByKey")
	@SysLog("'关键字查找场景,key:' + #key")
	@PreAuth("hasPermit('{}searchByKey')")
	public R<List<Scene>> searchByKey(@RequestParam("key") String key) {
		return success(sceneService.findScenesByKey(key));
	}
}
