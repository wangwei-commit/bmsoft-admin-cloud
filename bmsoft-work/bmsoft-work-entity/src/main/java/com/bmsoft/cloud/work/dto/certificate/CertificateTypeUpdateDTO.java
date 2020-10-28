package com.bmsoft.cloud.work.dto.certificate;

import com.bmsoft.cloud.base.entity.SuperEntity;
import com.bmsoft.cloud.work.enumeration.inventory.VariableType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实体类 凭证类型
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
@ApiModel(value = "CertificateTypeUpdateDTO", description = "凭证类型")
public class CertificateTypeUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键", required = true)
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotEmpty(message = "名称不能为空")
	@Length(max = 32, message = "名称长度不能超过32")
	private String display;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "key（下拉选使用）")
	@Length(max = 32, message = "key长度不能超过32")
	private String key;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	private String description;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型", required = true)
	private VariableType certificateType;
	/**
	 * 类型详细
	 */
	@ApiModelProperty(value = "类型详细", required = true)
	@NotEmpty(message = "类型详细不能为空")
	private String  fieldString;

	/**
	 * 是否内置
	 */
	@ApiModelProperty(value = "是否内置")
	private Boolean isDefault;
}
