package com.bmsoft.cloud.work.dto.inventory;

import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_NAME_METHOD;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.bmsoft.cloud.base.entity.SuperEntity;
import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;

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
 * 实体类 清单源
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
@ApiModel(value = "SourceUpdateDTO", description = "清单源")
public class SourceUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键", required = true)
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

	/**
	 * 清单ID
	 * 
	 * @InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	 *                     RemoteData<String, String>
	 */
	@ApiModelProperty(value = "清单ID")
	@InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	private RemoteData<String, String> inventory;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@Length(max = 32, message = "名称长度不能超过32")
	private String name;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	private String description;
	/**
	 * 来源
	 */
	@ApiModelProperty(value = "来源")
	@Length(max = 32, message = "来源长度不能超过32")
	private String source;
	/**
	 * 来源详细
	 */
	@ApiModelProperty(value = "来源详细")
	private Map<String, Object> sourceDetails;
}
