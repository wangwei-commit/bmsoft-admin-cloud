package com.bmsoft.cloud.work.dto.script;

import com.bmsoft.cloud.base.entity.SuperEntity;
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
import java.util.List;
import java.util.Map;

import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.SCENE_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.SCENE_ID_NAME_METHOD;

/**
 * <p>
 * 实体类 剧本
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
@ApiModel(value = "ScriptsUpdateDTO", description = "剧本")
public class ScriptsUpdateDTO implements Serializable {

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
	private String name;

	/**
	 * 别名
	 */
	@ApiModelProperty(value = "别名", required = true)
	@NotEmpty(message = "别名不能为空")
	@Length(max = 32, message = "别名长度不能超过32")
	private String alias;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@Length(max = 128, message = "描述长度不能超过128")
	private String description;

	/**
	 * 场景ID
	 *
	 * @InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD)
	 *                     RemoteData<String, String>
	 */
	@ApiModelProperty(value = "场景ID")
	@NotNull(message = "场景ID不能为空")
	@InjectionField(api = SCENE_ID_CLASS, method = SCENE_ID_NAME_METHOD)
	private RemoteData<Long, String> scene;

	/**
	 * 适用平台
	 */
	@ApiModelProperty(value = "适用平台")
	@NotEmpty(message = "适用平台不能为空")
	private List<Map<String, Object>> platform;

	/**
	 * 依赖
	 */
	@ApiModelProperty(value = "依赖")
	@Length(max = 256, message = "依赖长度不能超过256")
	private String depend;
	/**
	 * Tags
	 */
	@ApiModelProperty(value = "Tags")
	@Length(max = 256, message = "依赖长度不能超过256")
	private String tags;

	/**
	 * 入参
	 */
	@ApiModelProperty(value = "入参")
	private List<Map<String, Object>> inParam;

	/**
	 * 出参
	 */
	@ApiModelProperty(value = "出参")
	private List<Map<String, Object>> outParam;

}
