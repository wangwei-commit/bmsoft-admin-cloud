package com.bmsoft.cloud.work.controller.certificate;

import static com.bmsoft.cloud.work.util.TypeUtil.handlerRemoveField;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.bmsoft.cloud.database.mybatis.conditions.Wraps;
import com.bmsoft.cloud.database.mybatis.conditions.query.LbqWrapper;
import com.bmsoft.cloud.work.entity.template.Template;
import com.bmsoft.cloud.work.service.template.TemplateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.log.annotation.SysLog;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.common.CurrentUserOperate;
import com.bmsoft.cloud.work.dto.certificate.CertificatePageDTO;
import com.bmsoft.cloud.work.dto.certificate.CertificateSaveDTO;
import com.bmsoft.cloud.work.dto.certificate.CertificateUpdateDTO;
import com.bmsoft.cloud.work.entity.certificate.Certificate;
import com.bmsoft.cloud.work.properties.TypeProperties.Type;
import com.bmsoft.cloud.work.properties.TypeProperties.TypeField;
import com.bmsoft.cloud.work.service.certificate.CertificateService;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@RequestMapping("/certificate")
@Api(value = "Certificate", tags = "凭证")
@PreAuth(replace = "certificate:")
public class CertificateController extends
		SuperCacheController<CertificateService, Long, Certificate, CertificatePageDTO, CertificateSaveDTO, CertificateUpdateDTO> {

	@Resource
	private CurrentUserOperate currentUserOperate;
	@Resource
	private TemplateService templateService;
	@SuppressWarnings("unchecked")
	@Override
	public R<Certificate> handlerUpdate(CertificateUpdateDTO model) {
		return handlerRemoveField(model.getTypeDetails(),
				StrUtil.isNotBlank(model.getType()) ? baseService.getTypeFieldByType(model.getType())
						: ListUtil.empty(),
				model, dto -> super.handlerUpdate((CertificateUpdateDTO) dto));
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Certificate> handlerSave(CertificateSaveDTO model) {
		return handlerRemoveField(model.getTypeDetails(),
				StrUtil.isNotBlank(model.getType()) ? baseService.getTypeFieldByType(model.getType())
						: ListUtil.empty(),
				model, dto -> super.handlerSave((CertificateSaveDTO) dto));
	}

	@Override
	public void handlerWrapper(QueryWrap<Certificate> wrapper, PageParams<CertificatePageDTO> params) {
		//currentUserOperate.setQueryWrapByOrg(wrapper, getDbField("org", getEntityClass()));
		super.handlerWrapper(wrapper, params);
	}

	@ApiOperation(value = "凭证类型")
	@GetMapping("/type")
	@SysLog("'凭证类型'")
	@PreAuth("hasPermit('{}viewType')")
	public R<List<Type>> getCertificateType() {
		return success(baseService.getTypeList());
	}

	@ApiOperation(value = "凭证类型字段")
	@GetMapping("/type/{type}")
	@SysLog("'类型:' + #type")
	@PreAuth("hasPermit('{}viewTypeField')")
	public R<List<TypeField>> getTypeField(@PathVariable("type") String type) {
		return success(baseService.getTypeFieldByType(type));
	}

	@Override
	public R<Boolean> handlerDelete(List<Long> longs) {
		LbqWrapper<Template> query = Wraps.<Template>lbQ().in(Template::getCertificates, longs);
		List<Template> templates = templateService.list(query);
		if(templates!=null&&!templates.isEmpty()){
			return R.fail(400, "凭证已关联作业模板，不可删除");
		}
		return  super.handlerDelete(longs);
	}

	/**
	 * Excel导入后的操作
	 *
	 * @param list
	 */
	@Override
	public R<Boolean> handlerImport(List<Map<String, String>> list) {
		List<Certificate> certificateList = list.stream().map((map) -> {
			Certificate certificate = Certificate.builder().build();
			// TODO 请在这里完成转换
			return certificate;
		}).collect(Collectors.toList());

		return R.success(baseService.saveBatch(certificateList));
	}
}
