package com.bmsoft.cloud.work.dto.template;

import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@ApiModel(value = "TemplatePageDTO", description = "作业模板")
public class TemplatePageDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@Length(max = 32, message = "名称长度不能超过32")
	private String name;
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
	 * 凭证ID
	 *
	 * @InjectionField(api = CERTIFICATE_ID_CLASS, method =
	 *                     CERTIFICATE_ID_NAME_METHOD) RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "凭证ID")
	@InjectionField(api = CERTIFICATE_ID_CLASS, method = CERTIFICATE_ID_NAME_METHOD)
	private RemoteData<Long, String> certificates;

	@ApiModelProperty(value = "场景ID")
	@NotNull(message = "场景ID不能为空")
	@InjectionField(api = SCENE_ID_CLASS, method = SCENE_ID_NAME_METHOD)
	private RemoteData<Long, String> scene;
}
