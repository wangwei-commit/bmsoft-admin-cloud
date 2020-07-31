package com.bmsoft.cloud.work.enumeration.inventory;

import com.bmsoft.cloud.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 实体注释中生成的类型枚举 清单
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "InventoryType", description = "类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InventoryType implements BaseEnum {

	/**
	 * STANDARD="标准清单"
	 */
	STANDARD("标准清单"),
	/**
	 * SMART="智能清单"
	 */
	SMART("智能清单"),;

	@ApiModelProperty(value = "描述")
	private String desc;

	public static InventoryType match(String val, InventoryType def) {
		for (InventoryType enm : InventoryType.values()) {
			if (enm.name().equalsIgnoreCase(val)) {
				return enm;
			}
		}
		return def;
	}

	public static InventoryType get(String val) {
		return match(val, null);
	}

	public boolean eq(String val) {
		return this.name().equalsIgnoreCase(val);
	}

	public boolean eq(InventoryType val) {
		if (val == null) {
			return false;
		}
		return eq(val.name());
	}

	@Override
	@ApiModelProperty(value = "编码", allowableValues = "STANDARD,SMART", example = "STANDARD")
	public String getCode() {
		return this.name();
	}

}
