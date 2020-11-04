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
 * 实体注释中生成的类型枚举 作业模板
 * </p>
 *
 * @author bmsoft
 * @date 2020-07-24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TemplateFileType", description = "作业模板类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TemplateFileType implements BaseEnum {

	/**
	 * SCENARIOS="scenarios"
	 */
	SCENARIOS("scenarios"),
	/**
	 * SCRIPTS="scripts"
	 */
	SCRIPTS("scripts");


	@ApiModelProperty(value = "描述")
	private String desc;

	public static TemplateFileType match(String val, TemplateFileType def) {
		for (TemplateFileType enm : TemplateFileType.values()) {
			if (enm.name().equalsIgnoreCase(val)) {
				return enm;
			}
		}
		return def;
	}

	public static TemplateFileType get(String val) {
		return match(val, null);
	}

	public boolean eq(String val) {
		return this.name().equalsIgnoreCase(val);
	}

	public boolean eq(TemplateFileType val) {
		if (val == null) {
			return false;
		}
		return eq(val.name());
	}

	@Override
	@ApiModelProperty(value = "脚本/剧本", allowableValues = "SCENARIOS,SCRIPTS", example = "SCENARIOS")
	public String getCode() {
		return this.name();
	}

}
