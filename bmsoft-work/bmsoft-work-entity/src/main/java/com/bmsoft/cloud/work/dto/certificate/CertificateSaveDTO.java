package com.bmsoft.cloud.work.dto.certificate;

import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_FEIGN_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.ORG_ID_NAME_METHOD;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
 * 实体类 凭证
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
@ApiModel(value = "CertificateSaveDTO", description = "凭证")
public class CertificateSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

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
	 * 组织ID
	 * 
	 * @InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	 *                     RemoteData<Long, String>
	 */
	@ApiModelProperty(value = "组织ID", required = true)
	@NotNull(message = "组织ID不能为空")
	@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD)
	private RemoteData<Long, String> org;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型", required = true)
	@NotEmpty(message = "类型不能为空")
	@Length(max = 32, message = "类型长度不能超过32")
	private String type;
	/**
	 * 类型详细
	 */
	@ApiModelProperty(value = "类型详细", required = true)
	@NotEmpty(message = "类型详细不能为空")
	private Map<String, Object> typeDetails;

}
