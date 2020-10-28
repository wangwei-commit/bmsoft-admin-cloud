package com.bmsoft.cloud.work.dto.script;

import com.bmsoft.cloud.injection.annonation.InjectionField;
import com.bmsoft.cloud.model.RemoteData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@ApiModel(value = "ScriptsPageDTO", description = "脚本")
public class ScriptsPageDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@Length(max = 32, message = "名称长度不能超过32")
	private String name;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "别名")
	@Length(max = 32, message = "别名长度不能超过32")
	private String alias;

	@ApiModelProperty(value = "场景ID")
	@NotNull(message = "场景ID不能为空")
	@InjectionField(api = SCENE_ID_CLASS, method = SCENE_ID_NAME_METHOD)
	private RemoteData<Long, String> scene;
}
