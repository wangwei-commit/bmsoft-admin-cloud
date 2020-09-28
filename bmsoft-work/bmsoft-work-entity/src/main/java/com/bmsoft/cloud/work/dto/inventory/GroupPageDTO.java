package com.bmsoft.cloud.work.dto.inventory;

import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_CLASS;
import static com.bmsoft.cloud.common.constant.InjectionFieldConstants.INVENTORY_ID_NAME_METHOD;

import java.io.Serializable;
import java.util.List;

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
 * 实体类 清单组
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
@ApiModel(value = "GroupPageDTO", description = "清单组")
public class GroupPageDTO implements Serializable {

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
	@ApiModelProperty(value = "名称")
	@Length(max = 32, message = "名称长度不能超过32")
	private String name;
	/**
	 * 父组ID
	 */
	@ApiModelProperty(value = "父组ID")
	private Long parent;
	/**
	 * 忽略ID集
	 */
	@ApiModelProperty(value = "忽略ID集")
	private List<Long> ignoreIds;

}
