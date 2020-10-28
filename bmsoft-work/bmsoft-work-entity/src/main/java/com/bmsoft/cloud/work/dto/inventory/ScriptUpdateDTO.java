package com.bmsoft.cloud.work.dto.inventory;

import com.bmsoft.cloud.base.entity.SuperEntity;
import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_FEIGN_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_NAME_METHOD;

/**
 * <p>
 * 实体类 清单脚本
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
@ApiModel(value = "ScriptUpdateDTO", description = "清单脚本")
public class ScriptUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

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
	 * 组织ID
	 *
	 * @InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	 *                     RemoteData<String, String>
	 */
	@ApiModelProperty(value = "组织ID")
	@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	private RemoteData<Long, String> org;
	/**
	 * 脚本
	 */
	@ApiModelProperty(value = "脚本")
	private String script;
}
