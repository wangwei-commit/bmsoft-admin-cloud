package com.bmsoft.cloud.work.controller.inventory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.common.CurrentUserOperate;
import com.bmsoft.cloud.work.dto.inventory.ScriptPageDTO;
import com.bmsoft.cloud.work.dto.inventory.ScriptSaveDTO;
import com.bmsoft.cloud.work.dto.inventory.ScriptUpdateDTO;
import com.bmsoft.cloud.work.entity.inventory.Script;
import com.bmsoft.cloud.work.service.inventory.ScriptService;

import io.swagger.annotations.Api;

/**
 * <p>
 * 前端控制器 清单脚本
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Validated
@RestController
@RequestMapping("/script")
@Api(value = "Script", tags = "清单脚本")
@PreAuth(replace = "script:")
public class ScriptController
		extends SuperCacheController<ScriptService, Long, Script, ScriptPageDTO, ScriptSaveDTO, ScriptUpdateDTO> {

	private static final Integer SCRIPT_ERROR_CODE = 400;

	private static final String SCRIPT_ERROR_MESSAGE = "脚本格式不正确，请以#!开始";

	@Resource
	private CurrentUserOperate currentUserOperate;

	@Override
	public R<Script> handlerSave(ScriptSaveDTO model) {
		return handler(model.getScript(), model, dto -> super.handlerSave((ScriptSaveDTO) dto));
	}

	@Override
	public R<Script> handlerUpdate(ScriptUpdateDTO model) {
		return handler(model.getScript(), model, dto -> super.handlerUpdate((ScriptUpdateDTO) dto));
	}

	private R<Script> handler(String script, Serializable dto, Function<Serializable, R<Script>> function) {
		if (!script.startsWith("#!")) {
			return R.fail(SCRIPT_ERROR_CODE, SCRIPT_ERROR_MESSAGE);
		}
		return function.apply(dto);
	}

	@Override
	public void handlerWrapper(QueryWrap<Script> wrapper, PageParams<ScriptPageDTO> params) {
		currentUserOperate.setQueryWrapByOrg(wrapper, getDbField("org", getEntityClass()));
		super.handlerWrapper(wrapper, params);
	}

	/**
	 * Excel导入后的操作
	 *
	 * @param list
	 */
	@Override
	public R<Boolean> handlerImport(List<Map<String, String>> list) {
		List<Script> scriptList = list.stream().map((map) -> {
			Script script = Script.builder().build();
			// TODO 请在这里完成转换
			return script;
		}).collect(Collectors.toList());

		return R.success(baseService.saveBatch(scriptList));
	}
}
