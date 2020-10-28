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
@ApiModel(value = "SynStatus", description = "同步状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SynStatus implements BaseEnum {

	/**
	 * NORMAL="正常"
	 */
	NORMAL("正常"),
	/**
	 * FAILURE="失败"
	 */
	FAILURE("失败"),
	/**
	 * UNKNOWN="未知"
	 */
	UNKNOWN("未知"),;

	@ApiModelProperty(value = "描述")
	private String desc;

	public static SynStatus match(String val, SynStatus def) {
		for (SynStatus enm : SynStatus.values()) {
			if (enm.name().equalsIgnoreCase(val)) {
				return enm;
			}
		}
		return def;
	}

	public static SynStatus get(String val) {
		return match(val, null);
	}

	public boolean eq(String val) {
		return this.name().equalsIgnoreCase(val);
	}

	public boolean eq(SynStatus val) {
		if (val == null) {
			return false;
		}
		return eq(val.name());
	}

	@Override
	@ApiModelProperty(value = "编码", allowableValues = "NORMAL,FAILURE,UNKNOWN", example = "NORMAL")
	public String getCode() {
		return this.name();
	}

}
