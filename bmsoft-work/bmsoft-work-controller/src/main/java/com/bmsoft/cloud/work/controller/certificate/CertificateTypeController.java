package com.bmsoft.cloud.work.controller.certificate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.log.annotation.SysLog;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.dto.certificate.CertificateTypePageDTO;
import com.bmsoft.cloud.work.dto.certificate.CertificateTypeSaveDTO;
import com.bmsoft.cloud.work.dto.certificate.CertificateTypeUpdateDTO;
import com.bmsoft.cloud.work.dto.inventory.InventorySaveDTO;
import com.bmsoft.cloud.work.entity.certificate.CertificateType;
import com.bmsoft.cloud.work.entity.inventory.Inventory;
import com.bmsoft.cloud.work.enumeration.inventory.VariableType;
import com.bmsoft.cloud.work.service.certificate.CertificateTypeService;
import com.bmsoft.cloud.work.util.VariableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bmsoft.cloud.work.util.VariableUtil.handler;
import static com.bmsoft.cloud.work.util.VariableUtil.isYamlListValue;

/**
 * <p>
 * 前端控制器 凭证
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Validated
@RestController
@RequestMapping("/certificateType")
@Api(value = "CertificateType", tags = "凭证类型")
@PreAuth(replace = "certificateType:")
public class CertificateTypeController extends
		SuperCacheController<CertificateTypeService, Long, CertificateType, CertificateTypePageDTO, CertificateTypeSaveDTO, CertificateTypeUpdateDTO> {
	@Autowired
	protected CertificateTypeService baseService;
	@SuppressWarnings("unchecked")
	@Override
	public R<CertificateType> handlerUpdate(CertificateTypeUpdateDTO model) {
		model.setKey(model.getDisplay().toLowerCase());
		if(VariableType.YAML.eq(model.getCertificateType())){
			if(!isYamlListValue(model.getFieldString())){
				return R.fail(VariableUtil.VARIABLE_ERROR_CODE, VariableUtil.VARIABLE_ERROR_MESSAGE);
			}
			return super.handlerUpdate(model);
		}
		return handler(model.getCertificateType(), model.getFieldString(), model,
				dto -> super.handlerUpdate((CertificateTypeUpdateDTO) dto));
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<CertificateType> handlerSave(CertificateTypeSaveDTO model) {
		model.setKey(model.getDisplay().toLowerCase());
		if(VariableType.YAML.eq(model.getCertificateType())){
			if(!isYamlListValue(model.getFieldString())){
				return R.fail(VariableUtil.VARIABLE_ERROR_CODE, VariableUtil.VARIABLE_ERROR_MESSAGE);
			}
			return super.handlerSave(model);
		}
		return handler(model.getCertificateType(), model.getFieldString(), model,
				dto -> super.handlerSave((CertificateTypeSaveDTO) dto));
	}

	@Override
	public void handlerWrapper(QueryWrap<CertificateType> wrapper, PageParams<CertificateTypePageDTO> params) {
		super.handlerWrapper(wrapper, params);
	}
	@Override
	public void handlerResult(IPage<CertificateType> page) {
		 super.handlerResult(page);
		page.getRecords().stream().forEach(certificateType -> {
				if(VariableType.JSON.getCode().equals(certificateType.getCertificateType().getCode())){
					certificateType.setFields(JSONArray.parseArray(certificateType.getFieldString(),Map.class));
				}else{
					Yaml yaml = new Yaml();
					certificateType.setFields(yaml.load(certificateType.getFieldString()));
				}
			 });
	}

	@ApiOperation(value = "查询转换后数据")
	@PostMapping("/queryChangeData")
	@PreAuth("hasPermit('{}queryChangeData')")

	public R<List<CertificateType>> queryChangeData() {
		List<CertificateType> list =  baseService.list();
				list.stream().forEach(certificateType -> {
			if(VariableType.JSON.getCode().equals(certificateType.getCertificateType().getCode())){
				certificateType.setFields(JSONArray.parseArray(certificateType.getFieldString(),Map.class));
			}else{
				Yaml yaml = new Yaml();
				certificateType.setFields(yaml.load(certificateType.getFieldString()));
			}
		});
		return R.success(list);
	}
	/**
	 * Excel导入后的操作
	 *
	 * @param list
	 */
	@Override
	public R<Boolean> handlerImport(List<Map<String, String>> list) {
		List<CertificateType> certificateList = list.stream().map((map) -> {
			CertificateType certificateType = CertificateType.builder().build();
			// TODO 请在这里完成转换
			return certificateType;
		}).collect(Collectors.toList());

		return R.success(baseService.saveBatch(certificateList));
	}


}
