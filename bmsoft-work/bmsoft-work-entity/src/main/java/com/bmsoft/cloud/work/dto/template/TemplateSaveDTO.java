package com.bmsoft.cloud.work.dto.template;

import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import com.bmsoft.cloud.work.enumeration.inventory.TemplateFileType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.*;

/**
 * <p>
 * 实体类 作业模板
 * </p>
 *
 * @author bmsoft
 * @since 2020-10-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "TemplateSaveDTO", description = "作业模板")
public class TemplateSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@NotEmpty(message = "名称不能为空")
	@Length(max = 32, message = "名称长度不能超过32")
	private String name;


	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 256, message = "描述长度不能超过256")
	private String description;
	/**
	 * 清单ID
	 *
	 * @InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	 *                     RemoteData<String, String>
	 */
	@ApiModelProperty(value = "清单ID")
	@NotNull(message = "清单ID不能为空")
	@InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	private RemoteData<Long, String> inventory;

	/**
	 * 过滤主机
	 */
	@ApiModelProperty(value = "过滤主机")
	@Length(max = 256, message = "过滤主机长度不能超过256")
	private String filterHost;

	/**
	 * 凭证ID
	 *
	 * @InjectionField(api = CERTIFICATE_ID_CLASS, method =
	 *                     CERTIFICATE_ID_NAME_METHOD) RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "凭证ID")
	@InjectionField(api = CERTIFICATE_ID_CLASS, method = CERTIFICATE_ID_NAME_METHOD)
	private RemoteData<Long, String> certificates;

	/**
	 * 脚本类型 #ScriptType{SCENARIOS:scenarios;SCRIPTS:scripts}
	 */
	@ApiModelProperty(value = "脚本类型")
	@NotNull(message = "脚本类型")
	private TemplateFileType templateFileType;


	/**
	 * 脚本/剧本ID
	 *
	 * @InjectionField(api = CERTIFICATE_ID_CLASS, method =
	 *                     CERTIFICATE_ID_NAME_METHOD) RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "脚本/剧本ID")
	@InjectionField(api = TEMPLATE_ID_CLASS, method = TEMPLATE_ID_NAME_METHOD)
	private RemoteData<Long, String> scriptId;


	/**
	 * work_tag
	 */
	@ApiModelProperty(value = "work_tag")
	@Length(max = 256, message = "依赖长度不能超过256")
	private String workTag;

	/**
	 * skip_tag
	 */
	@ApiModelProperty(value = "skip_tag")
	@Length(max = 256, message = "依赖长度不能超过256")
	private String skipTag;

	/**
	 * 分片
	 */
	@ApiModelProperty(value = "分片")
	@Max(9999)
	private Integer zone;

	/**
	 * 参数
	 */
	@ApiModelProperty(value = "参数")
	private Map<String, Object> param;


}
