package com.bmsoft.cloud.work.dto.inventory;

import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_NAME_METHOD;

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
@ApiModel(value = "SourceSaveDTO", description = "清单源")
public class SourceSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 清单ID
	 *
	 * @InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	 *                     RemoteData<String, String>
	 */
	@ApiModelProperty(value = "清单ID", required = true)
	@NotNull(message = "清单ID不能为空")
	@InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	private RemoteData<Long, String> inventory;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotEmpty(message = "名称不能为空")
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
	@ApiModelProperty(value = "来源", required = true)
	@NotEmpty(message = "来源不能为空")
	@Length(max = 32, message = "来源长度不能超过32")
	private String source;
	/**
	 * 来源详细
	 */
	@ApiModelProperty(value = "来源详细", required = true)
	@NotEmpty(message = "来源详细不能为空")
	private Map<String, Object> sourceDetails;

}
