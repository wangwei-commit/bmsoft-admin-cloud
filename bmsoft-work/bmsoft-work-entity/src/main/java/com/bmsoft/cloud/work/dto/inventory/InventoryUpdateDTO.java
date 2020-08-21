package com.bmsoft.cloud.work.dto.inventory;

import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.CERTIFICATE_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.CERTIFICATE_ID_NAME_METHOD;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_FEIGN_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_NAME_METHOD;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.bmsoft.cloud.base.entity.SuperEntity;
import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import com.bmsoft.cloud.work.enumeration.inventory.InventoryType;
import com.bmsoft.cloud.work.enumeration.inventory.VariableType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实体类 清单
 * </p>
 *
 * @author bmsoft
 * @since 2020-07-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InventoryUpdateDTO", description = "清单")
public class InventoryUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键", required = true)
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@Length(max = 32, message = "名称长度不能超过32")
	private String name;
	/**
	 * 类型 #InventoryType{STANDARD:标准清单;SMART:智能清单}
	 */
	@ApiModelProperty(value = "类型")
	private InventoryType type;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	private String description;
	/**
	 * 组织ID
	 * 
	 * @InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	 *                     RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "组织ID")
	@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	private RemoteData<Long, String> org;
	/**
	 * 凭证ID
	 * 
	 * @InjectionField(api = CERTIFICATE_ID_CLASS, method =
	 *                     CERTIFICATE_ID_NAME_METHOD) RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "凭证ID")
	@InjectionField(api = CERTIFICATE_ID_CLASS, method = CERTIFICATE_ID_NAME_METHOD)
	private List<RemoteData<Long, String>> certificates;
	/**
	 * 变量类型 #VariableType{YAML:yaml;JSON:json}
	 */
	@ApiModelProperty(value = "变量类型")
	private VariableType variableType;
	/**
	 * 变量值
	 */
	@ApiModelProperty(value = "变量值")
	private String variableValue;
}
