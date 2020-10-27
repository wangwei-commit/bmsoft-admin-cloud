package com.bmsoft.cloud.work.controller.certificate;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.work.dto.certificate.CertificateTypePageDTO;
import com.bmsoft.cloud.work.dto.certificate.CertificateTypeSaveDTO;
import com.bmsoft.cloud.work.dto.certificate.CertificateTypeUpdateDTO;
import com.bmsoft.cloud.work.dto.inventory.InventorySaveDTO;
import com.bmsoft.cloud.work.entity.certificate.CertificateType;
import com.bmsoft.cloud.work.service.certificate.CertificateTypeService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bmsoft.cloud.work.util.VariableUtil.handler;

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
//@PreAuth(replace = "certificateType:")
public class CertificateTypeController extends
		SuperCacheController<CertificateTypeService, Long, CertificateType, CertificateTypePageDTO, CertificateTypeSaveDTO, CertificateTypeUpdateDTO> {

	@SuppressWarnings("unchecked")
	@Override
	public R<CertificateType> handlerUpdate(CertificateTypeUpdateDTO model) {
		return handler(model.getCertificateType(), model.getFields().toString(), model,
				dto -> super.handlerUpdate((CertificateTypeUpdateDTO) dto));
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<CertificateType> handlerSave(CertificateTypeSaveDTO model) {
		model.setDefault(false);
		model.setKey(model.getDisplay().toLowerCase());
		return handler(model.getCertificateType(), model.getFields().toString(), model,
				dto -> super.handlerSave((CertificateTypeSaveDTO) dto));
	}

	@Override
	public void handlerWrapper(QueryWrap<CertificateType> wrapper, PageParams<CertificateTypePageDTO> params) {
		super.handlerWrapper(wrapper, params);
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
