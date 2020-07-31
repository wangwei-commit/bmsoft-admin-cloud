package com.bmsoft.cloud.work.dto.inventory;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.bmsoft.cloud.work.enumeration.inventory.InventoryType;

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
@ApiModel(value = "InventoryPageDTO", description = "清单")
public class InventoryPageDTO implements Serializable {

	private static final long serialVersionUID = 1L;

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

}
