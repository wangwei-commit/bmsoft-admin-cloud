package com.bmsoft.cloud.work.controller.script;

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
import com.bmsoft.cloud.work.dto.script.ScenariosPageDTO;
import com.bmsoft.cloud.work.dto.script.ScenariosSaveDTO;
import com.bmsoft.cloud.work.dto.script.ScenariosUpdateDTO;
import com.bmsoft.cloud.work.entity.scripts.Scenarios;
import com.bmsoft.cloud.work.entity.template.Template;
import com.bmsoft.cloud.work.properties.TypeProperties;
import com.bmsoft.cloud.work.service.script.ScenariosService;
/*import com.bmsoft.cloud.work.service.template.TemplateService;*/
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

;

/**
 * <p>
 * 前端控制器 脚本
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
@Validated
@RestController
@RequestMapping("/scenarios")
@Api(value = "Scenarios", tags = "脚本")
@PreAuth(replace = "scenarios:")
public class ScenariosController extends
		SuperCacheController<ScenariosService, Long, Scenarios, ScenariosPageDTO, ScenariosSaveDTO, ScenariosUpdateDTO> {

	@Resource
	public ScenariosService scenariosService;
	/*@Resource
	private TemplateService templateService;*/
	@SuppressWarnings("unchecked")
	@Override
	public R<Scenarios> handlerSave(ScenariosSaveDTO model) {
		return   super.handlerSave(model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Scenarios> handlerUpdate(ScenariosUpdateDTO model) {
		return  super.handlerUpdate(model);
	}

	@Override
	public R<Boolean> handlerDelete(List<Long> longs) {
		/*LbqWrapper<Template> query = Wraps.<Template>lbQ().in(Template::getScriptId, longs);
		List<Template> templates = templateService.list(query);
		if(templates!=null&&!templates.isEmpty()){
			return R.fail(400, "脚本已关联作业模板，不可删除");
		}*/
		return  super.handlerDelete(longs);
	}

	@Override
	public void handlerWrapper(QueryWrap<Scenarios> wrapper, PageParams<ScenariosPageDTO> params) {
		super.handlerWrapper(wrapper, params);
	}

	@Override
	public void handlerResult(IPage<Scenarios> page) {
		super.handlerResult(page);
	}

	@ApiOperation(value = "复制脚本")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "脚本ID", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "name", value = "名称", dataType = "String", paramType = "query", required = true),
			@ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "query")})
	@PostMapping("/copy")
	@SysLog("'复制脚本,id:' + #id+' name: '+#name+' description:'+#description")
	@PreAuth("hasPermit('{}copyModel')")
	public R<Scenarios> copyModel(@RequestParam("id") Long id,@RequestParam("name") String name,@RequestParam("description") String description) {
			Scenarios scenarios = scenariosService.getByIdCache(id);
			ScenariosSaveDTO saveDTO = JSONObject.parseObject(JSONObject.toJSONString(scenarios),ScenariosSaveDTO.class);
			saveDTO.setName(name);
			saveDTO.setDescription(description);
			return super.save(saveDTO);
	}

	@ApiOperation(value = "平台来源")
	@GetMapping("/platFormSource")
	@SysLog("'平台来源'")
	@PreAuth("hasPermit('{}getPlatFormSource')")
	public R<List<TypeProperties.PlateForm>> getPlatFormSource() {
		return success(scenariosService.getTypeList());
	}

}
