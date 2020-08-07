package com.bmsoft.cloud.work.controller.inventory;

import static com.bmsoft.cloud.work.util.TypeUtil.vaildType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.log.annotation.SysLog;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.dto.inventory.SourcePageDTO;
import com.bmsoft.cloud.work.dto.inventory.SourceSaveDTO;
import com.bmsoft.cloud.work.dto.inventory.SourceUpdateDTO;
import com.bmsoft.cloud.work.entity.inventory.Source;
import com.bmsoft.cloud.work.properties.TypeProperties.Type;
import com.bmsoft.cloud.work.properties.TypeProperties.TypeField;
import com.bmsoft.cloud.work.service.inventory.SourceService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器 清单源
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Validated
@RestController
@RequestMapping("/source")
@Api(value = "Source", tags = "清单源")
@PreAuth(replace = "source:")
public class SourceController
		extends SuperCacheController<SourceService, Long, Source, SourcePageDTO, SourceSaveDTO, SourceUpdateDTO> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public R<Source> handlerUpdate(SourceUpdateDTO model) {
		if(StrUtil.isNotBlank(model.getSource())) {
			R r = vaildType(model.getSourceDetails(), baseService.getTypeFieldByType(model.getSource()));
			if (r != null) {
				return r;
			}
		}
		return super.handlerUpdate(model);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public R<Source> handlerSave(SourceSaveDTO model) {
		if(StrUtil.isNotBlank(model.getSource())) {
			R r = vaildType(model.getSourceDetails(), baseService.getTypeFieldByType(model.getSource()));
			if (r != null) {
				return r;
			}
		}
		return super.handlerSave(model);
	}

	@Override
	public void query(PageParams<SourcePageDTO> params, IPage<Source> page, Long defSize) {
		if (params.getModel().getInventory() != null && params.getModel().getInventory().getKey() != null) {
			super.query(params, page, defSize);
		}
	}

	@ApiOperation(value = "清单源类型")
	@GetMapping("/type")
	@SysLog("'清单源类型'")
	@PreAuth("hasPermit('{}viewType')")
	public R<List<Type>> getSourceType() {
		return success(baseService.getSourceTypeList());
	}

	@ApiOperation(value = "清单源类型字段")
	@GetMapping("/type/{type}")
	@SysLog("'类型:' + #type")
	@PreAuth("hasPermit('{}viewTypeField')")
	public R<List<TypeField>> getTypeField(@PathVariable("type") String type) {
		return success(baseService.getTypeFieldByType(type));
	}

	/**
	 * Excel导入后的操作
	 *
	 * @param list
	 */
	@Override
	public R<Boolean> handlerImport(List<Map<String, String>> list) {
		List<Source> sourceList = list.stream().map((map) -> {
			Source source = Source.builder().build();
			// TODO 请在这里完成转换
			return source;
		}).collect(Collectors.toList());

		return R.success(baseService.saveBatch(sourceList));
	}
}
