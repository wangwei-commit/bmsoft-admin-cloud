package com.bmsoft.cloud.work.controller.template;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.log.annotation.SysLog;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.dto.script.ScenariosSaveDTO;
import com.bmsoft.cloud.work.dto.template.TemplatePageDTO;
import com.bmsoft.cloud.work.dto.template.TemplateSaveDTO;
import com.bmsoft.cloud.work.dto.template.TemplateUpdateDTO;
import com.bmsoft.cloud.work.entity.*;
import com.bmsoft.cloud.work.entity.certificate.Certificate;
import com.bmsoft.cloud.work.entity.inventory.*;
import com.bmsoft.cloud.work.entity.scripts.Scenarios;
import com.bmsoft.cloud.work.entity.scripts.Scripts;
import com.bmsoft.cloud.work.entity.template.Template;
import com.bmsoft.cloud.work.enumeration.inventory.TemplateFileType;
import com.bmsoft.cloud.work.properties.TypeProperties;
import com.bmsoft.cloud.work.service.certificate.CertificateService;
import com.bmsoft.cloud.work.service.inventory.*;
import com.bmsoft.cloud.work.service.script.ScenariosService;
import com.bmsoft.cloud.work.service.script.ScriptsService;
import com.bmsoft.cloud.work.service.template.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

;

/**
 * <p>
 * 前端控制器 作业模板
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
@Validated
@RestController
@RequestMapping("/template")
@Api(value = "Template", tags = "作业模板")
//@PreAuth(replace = "template:")
public class TemplateController extends
		SuperCacheController<TemplateService, Long, Template, TemplatePageDTO, TemplateSaveDTO, TemplateUpdateDTO> {

	@Resource
	private TypeProperties typeProperties;
	@Resource
	private GroupService groupService;
	@Resource
	private GroupHostService groupHostService;
	@Resource
	private GroupParentService groupParentService;
	@Resource
	private SourceService sourceService;
	@Resource
	private CertificateService certificateService;
	@Resource
	private ScenariosService scenariosService;
	@Resource
	private ScriptsService scriptsService;

	@Resource
	private HostService hostService;
	@Resource
	private TemplateService templateService;

	@SuppressWarnings("unchecked")
	@Override
	public R<Template> handlerSave(TemplateSaveDTO model) {
		/*try {
			sendTemplate(model);
		}catch (Exception e){
			return R.fail(400,e.getMessage());
		}*/

		R<Template> result= super.handlerSave(model);

		return   result;
	}

	private void sendTemplate(TemplateSaveDTO model){
		String methodUrl = typeProperties.getTemplateUrl() ;
		TemplateEntity entity = new TemplateEntity();
		entity.setKwargs(new Kwargs());


		List<Long> groupIds = groupService.list(Wraps.lbQ(Group.builder().build()).eq(Group::getInventory,
				model.getInventory().getKey())).stream().map(Group::getId).collect(Collectors.toList());
		//过滤子组
		entity.getKwargs().setGroups(new ArrayList<>());
		List<Long> childGroupIds = groupParentService.list(Wraps.lbQ(GroupParent.builder().build()).in(GroupParent::getFromId,
				groupIds).ne(GroupParent::getToId,0)).stream().map(GroupParent::getFromId).collect(Collectors.toList());
		if(groupIds!=null&&!groupIds.isEmpty()){
			groupIds.removeAll(childGroupIds);
			List<Group> groups = groupService.listByIds(groupIds);
			groups.stream().forEach(group -> {
				InventoryEntity inventoryEntity = new InventoryEntity();
				constructEntity(group,inventoryEntity);
				entity.getKwargs().getGroups().add(inventoryEntity);
			});
		}
		List<Long> hostIds = hostService.list(Wraps.lbQ(Host.builder().build()).eq(Host::getInventory,
				model.getInventory().getKey())).stream().map(Host::getId).collect(Collectors.toList());
		//没有组的主机
		List<Long> noGroupHostIds = groupHostService.list(Wraps.lbQ(GroupHost.builder().build()).in(GroupHost::getHostId,
				hostIds).eq(GroupHost::getGroupId,0)).stream().map(GroupHost::getHostId).collect(Collectors.toList());

		List<HostEntity> hostEntities = new ArrayList<>();
		if(!noGroupHostIds.isEmpty()){
			hostService.listByIds(noGroupHostIds).stream().forEach(host -> {
				HostEntity hostEntity = new HostEntity();
				hostEntity.setName(host.getName());
				hostEntity.setType(host.getVariableType().getDesc());
				hostEntity.setCode(host.getVariableValue());
				hostEntities.add(hostEntity);
			});
		}

		List<Source> sources = sourceService.list(Wraps.lbQ(Source.builder().build()).eq(Source::getInventory,
				model.getInventory().getKey()));
		List<SourceEntity> sourceEntities = new ArrayList<>();
		sources.stream().forEach(source -> {
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setName(source.getName());
			String sourceType= source.getSource();
			if("custom".equals(sourceType)){
				sourceEntity.setType(1);
			}else {
				sourceEntity.setType(0);
			}
			//测试数据(开始)
			sourceEntity.setType(1);
			sourceEntity.setCode("print(hello world)");
			sourceEntity.setCodeType("python");
			//测试数据（结束）
			Map<String, Object> sourceDetails = source.getSourceDetails();
			if(sourceDetails.containsKey("variable")){
				JSONObject object = JSONObject.parseObject(sourceDetails.get("variable").toString());
				sourceEntity.setEnvCode(object.getString("value"));
				sourceEntity.setEnvType(object.getString("type"));
			}
			sourceEntities.add(sourceEntity);
		});
		entity.getKwargs().setDynamiclist(sourceEntities);
		entity.getKwargs().setHosts(hostEntities);
		entity.getKwargs().setTemplateFileType(model.getTemplateFileType());
		if (TemplateFileType.SCENARIOS.eq(model.getTemplateFileType())){
			Scenarios scenarios = scenariosService.getByIdCache(model.getScriptId().getKey());
			entity.getKwargs().setScenarios(scenarios);
			//todo

			//entity.getKwargs().setOutParam(scenarios.getOutParam());
		}else {
			Scripts scripts = scriptsService.getByIdCache(model.getScriptId().getKey());
			entity.getKwargs().setScripts(scripts);
			//todo

			//entity.getKwargs().setOutParam(scripts.getOutParam());
		}
		entity.getKwargs().setInParam(model.getParam());
		entity.getKwargs().setFilterHost(model.getFilterHost());
		Certificate certificate= certificateService.getById(model.getCertificates().getKey());
		entity.getKwargs().setVars(certificate.getTypeDetails());
		entity.getKwargs().getVars().putAll(model.getParam());
		entity.getKwargs().setTags(model.getWorkTag());
		entity.getKwargs().setSkipTags(model.getSkipTag());
		imageIdentify2(methodUrl,entity);
	}

	private  void constructEntity(Group group, InventoryEntity inventoryEntity){
		inventoryEntity.setName(group.getName());
		List<Long> hostIds = groupHostService
				.list(Wraps.lbQ(GroupHost.builder().build()).eq(GroupHost::getGroupId,
						group.getId()))
				.stream().map(GroupHost::getHostId).collect(Collectors.toList());
		QueryWrap<Host> wrapper = new QueryWrap<>();
		if (!hostIds.isEmpty()) {
			wrapper.in("id", hostIds);
			List<Host> hosts = hostService.list(wrapper);
			inventoryEntity.setHosts(new ArrayList<>());
			hosts.stream().forEach(host -> {
				HostEntity hostEntity = new HostEntity();
				hostEntity.setName(host.getName());
				hostEntity.setType(host.getVariableType().getDesc());
				hostEntity.setCode(host.getVariableValue());
				inventoryEntity.getHosts().add(hostEntity);
			});
		}
		List<Long> groupIds = groupParentService.list(Wraps.lbQ(GroupParent.builder().build()).eq(GroupParent::getToId,
				group.getId())).stream().map(GroupParent::getFromId).collect(Collectors.toList());
		if(groupIds!=null&&!groupIds.isEmpty()){
			inventoryEntity.setChildren(new ArrayList<>());
			List<Group> groups = groupService.listByIds(groupIds);
			groups.stream().forEach(groupEntity -> {
				InventoryEntity inventory = new InventoryEntity();
				inventory.setName(groupEntity.getName());
				constructEntity(groupEntity,inventory);

				inventoryEntity.getChildren().add(inventory);
			});
		}

	}

	private  String imageIdentify2(String methodUrl, TemplateEntity entity) {
		HttpURLConnection connection = null;
		PrintWriter dataout = null;
		BufferedReader bf = null;
		String BOUNDARY = "----WebKitFormBoundary2NYA7hQkjRHg5WJk";
		String END_DATA = ("\r\n--" + BOUNDARY + "--\r\n");
		String BOUNDARY_PREFIX = "--";
		String NEW_LINE = "\r\n";
		String authString = typeProperties.getAuth() ;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		try {
			URL url = new URL(methodUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			connection.setDoOutput(true);// 设置连接输出流为true,默认false
			connection.setDoInput(true);// 设置连接输入流为true
			connection.setRequestMethod("POST");// 设置请求方式为post
			connection.setUseCaches(false);// post请求缓存设为false
			connection.setInstanceFollowRedirects(true);// 设置该HttpURLConnection实例是否自动执行重定向
			connection.setRequestProperty("connection", "Keep-Alive");// 连接复用
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
			connection.connect();// 建立连接

			dataout = new PrintWriter(connection.getOutputStream());// 创建输入输出流,用于往连接里面输出携带的参数


			dataout.write(JSONObject.toJSONString(entity));
			dataout.flush();
			dataout.close();

			bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 连接发起请求,处理服务器响应
			StringBuilder result = new StringBuilder(); // 用来存储响应数据
			// 循环读取流,若不到结尾处

			String bfs = null;
			while ((bfs=bf.readLine())!=null) {
				result.append(bfs).append(System.getProperty("line.separator"));
			}
			bf.close();
			connection.disconnect(); // 销毁连接
			System.out.println(result.toString().trim());
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Template> handlerUpdate(TemplateUpdateDTO model) {
		return  super.handlerUpdate(model);
	}

	@Override
	public R<Boolean> handlerDelete(List<Long> longs) {

		return  super.handlerDelete(longs);
	}

	@Override
	public void handlerWrapper(QueryWrap<Template> wrapper, PageParams<TemplatePageDTO> params) {
		super.handlerWrapper(wrapper, params);
	}

	@Override
	public void handlerResult(IPage<Template> page) {
		super.handlerResult(page);
	}

	@ApiOperation(value = "复制作业模板")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "作业模板ID", dataType = "Long", paramType = "query", required = true),
			@ApiImplicitParam(name = "name", value = "名称", dataType = "String", paramType = "query", required = true),
			@ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "query")})
	@PostMapping("/copy")
	@SysLog("'复制作业模板,id:' + #id+' name: '+#name+' description:'+#description")
	@PreAuth("hasPermit('{}copyTemplate')")
	public R<Template> copyTemplate(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("description") String description) {

		Template template = templateService.getByIdCache(id);
		template.setDescription(description);
		template.setName(name);
		template.setId(null);
		template.setCreateUser(null);
		template.setCreateTime(null);
		template.setUpdateTime(null);
		template.setUpdateUser(null);
		TemplateSaveDTO saveDTO = JSONObject.parseObject(JSONObject.toJSONString(template),TemplateSaveDTO.class);
		return super.save(saveDTO);
	}
	@ApiOperation(value = "启动作业")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids[]", value = "作业模板ID", dataType = "array", paramType = "query", required = true)})
	@PostMapping("/startTemplate")
	@SysLog("'启动作业,ids:' + #ids'")
	@PreAuth("hasPermit('{}startTemplate')")
	public R<Boolean> startTemplate(@RequestParam("ids[]") List<Long> ids) {

		return R.success();
	}


}
