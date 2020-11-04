package com.bmsoft.cloud.work.util;

import static com.bmsoft.cloud.work.util.VariableUtil.VARIABLE_ERROR_CODE;
import static com.bmsoft.cloud.work.util.VariableUtil.VARIABLE_ERROR_MESSAGE;
import static com.bmsoft.cloud.work.util.VariableUtil.vaildVariable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.work.enumeration.inventory.VariableType;
import com.bmsoft.cloud.work.properties.TypeProperties.FieldType;
import com.bmsoft.cloud.work.properties.TypeProperties.TypeField;

import cn.hutool.core.util.ObjectUtil;

public class TypeUtil {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static R vaildType(Map<String, Object> details, List<TypeField> fields) {
		if (fields == null) {
			return R.fail(400, "来源不正确");
		}
		if (details == null) {
			return R.fail(400, "来源不为空时，来源详细也不能为空");
		}
		for (TypeField typeField : fields) {
			Object object = details.get(typeField.getField());
			if (typeField.getPrimary() && ObjectUtil.isEmpty(object)) {
				return R.fail(400, typeField.getDisplay() + "不能为空");
			}
			if (typeField.getLen() != null && typeField.getLen() > 0
					&& (FieldType.TEXT.equals(typeField.getFieldType())
							|| FieldType.PASSWORD.equals(typeField.getFieldType())
							|| FieldType.MULTI_TEXT.equals(typeField.getFieldType()))
					&& ObjectUtil.length(object) > typeField.getLen()) {
				return R.fail(400, typeField.getDisplay() + "长度不能超过" + typeField.getLen());
			}
			if (FieldType.VARIABLE.equals(typeField.getFieldType()) && object != null) {
				try {
					Map<String, String> variable = (Map<String, String>) object;
					if (!(variable.containsKey("type") && variable.containsKey("value"))) {
						return R.fail(400, typeField.getDisplay() + "不正确");
					}
					VariableType variableType = VariableType.get(variable.get("type"));
					if (variableType == null) {
						return R.fail(400, typeField.getDisplay() + "不正确");
					}
					if (!vaildVariable(variableType, variable.get("value"))) {
						return R.fail(VARIABLE_ERROR_CODE, VARIABLE_ERROR_MESSAGE);
					}
				} catch (Exception e) {
					return R.fail(400, typeField.getDisplay() + "不正确");
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static R handler(Map<String, Object> typeDetails, List<TypeField> fields, Serializable dto, Function<Serializable, R> function) {
		R r = vaildType(typeDetails, fields);
		if (r != null) {
			return r;
		}
		return function.apply(dto);
	}
	public static R handlerRemoveField(Map<String, Object> typeDetails, List<TypeField> fields, Serializable dto, Function<Serializable, R> function) {
		R r = vaildType(typeDetails, fields);
		if (r != null) {
			return r;
		}
		removeFieldValue(typeDetails, fields);
		return function.apply(dto);
	}
	public static void removeFieldValue(Map<String, Object> details, List<TypeField> fields) {

		for (TypeField typeField : fields) {
			Object object = details.get(typeField.getField());

			if (FieldType.PASSWORD.equals(typeField.getFieldType()) && object != null) {
				details.remove(typeField.getFieldType());
			}
		}
	}

}
