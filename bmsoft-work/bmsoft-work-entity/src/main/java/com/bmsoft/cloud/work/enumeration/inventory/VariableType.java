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
 * 实体注释中生成的类型枚举 清单主机
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "VariableType", description = "变量类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum VariableType implements BaseEnum {

	/**
	 * YAML="yaml"
	 */
	YAML("yaml"),
	/**
	 * JSON="json"
	 */
	JSON("json"),;

	@ApiModelProperty(value = "描述")
	private String desc;

	public static VariableType match(String val, VariableType def) {
		for (VariableType enm : VariableType.values()) {
			if (enm.name().equalsIgnoreCase(val)) {
				return enm;
			}
		}
		return def;
	}

	public static VariableType get(String val) {
		return match(val, null);
	}

	public boolean eq(String val) {
		return this.name().equalsIgnoreCase(val);
	}

	public boolean eq(VariableType val) {
		if (val == null) {
			return false;
		}
		return eq(val.name());
	}

	@Override
	@ApiModelProperty(value = "编码", allowableValues = "YAML,JSON", example = "YAML")
	public String getCode() {
		return this.name();
	}

}
