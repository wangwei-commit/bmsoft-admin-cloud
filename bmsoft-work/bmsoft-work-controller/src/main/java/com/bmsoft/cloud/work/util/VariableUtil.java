package com.bmsoft.cloud.work.util;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.bmsoft.cloud.work.enumeration.inventory.VariableType;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VariableUtil {

	public static final Integer VARIABLE_ERROR_CODE = 400;

	public static final String VARIABLE_ERROR_MESSAGE = "变量值格式无效";
	
	public static boolean isJsonValue(String value) {
		return JSONUtil.isJson(value);
	}

	@SuppressWarnings("unused")
	public static boolean isYamlValue(String value) {
		try {
			if(StrUtil.isBlank(value)) {
				return false;
			}
			Yaml yaml = new Yaml();
			Map<String, Object> object = yaml.load(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean vaildVariable(VariableType type, String value) {
		log.info("VariableType = {}, VariableValue = {}", type, value);
		if ((VariableType.YAML.eq(type) && !VariableUtil.isYamlValue(value))
				|| (VariableType.JSON.eq(type) && !VariableUtil.isJsonValue(value))) {
			return false;
		}
		return true;
	}
	
}
