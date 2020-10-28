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
 * 实体注释中生成的类型枚举 脚本
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ScriptType", description = "脚本类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScriptType implements BaseEnum {

	/**
	 * YAML="yaml"
	 */
	YAML("yaml"),
	/**
	 * PYTHON="python"
	 */
	PYTHON("python"),
	/**
	 * SHELL="shell"
	 */
	SHELL("shell"),

	/**
	 * BAT="bat"
	 */
	BAT("bat"),

	/**
	 * POWER_SHELL="powerShell"
	 */
	POWER_SHELL("powerShell");


	@ApiModelProperty(value = "描述")
	private String desc;

	public static ScriptType match(String val, ScriptType def) {
		for (ScriptType enm : ScriptType.values()) {
			if (enm.name().equalsIgnoreCase(val)) {
				return enm;
			}
		}
		return def;
	}

	public static ScriptType get(String val) {
		return match(val, null);
	}

	public boolean eq(String val) {
		return this.name().equalsIgnoreCase(val);
	}

	public boolean eq(ScriptType val) {
		if (val == null) {
			return false;
		}
		return eq(val.name());
	}

	@Override
	@ApiModelProperty(value = "脚本类型", allowableValues = "YAML,PYTHON,SHELL,BAT,POWER_SHELL", example = "YAML")
	public String getCode() {
		return this.name();
	}

}
