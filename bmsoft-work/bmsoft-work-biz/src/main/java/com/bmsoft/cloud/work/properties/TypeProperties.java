package com.bmsoft.cloud.work.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

@Data
@RefreshScope
@ConfigurationProperties("bmsoft.work.type")
public class TypeProperties {

	private List<Type> inventorySource;

	private List<Type> certificateType;

	private List<PlateForm> platformSource;
	//剧本导入地址
	private String fileUrl;
	//作业模板同步地址
	private String templateUrl;

	//认证信息
	private String auth;
	@Data
	public static class Type {

		private String key;

		private String display;

		private List<TypeField> fields;
	}
	@Data
	public static class PlateForm {

		private String id;

		private String label;

		private List<PlateForm> children;
	}
	@Data
	public static class TypeField {

		private String display;

		private String field;

		private FieldType fieldType;

		private Boolean primary;

		private Integer len;

		private List<SelectData> data;

		private Boolean isDateInterval;

		private Boolean isMulti;

		private Boolean isPWRun;
	}

	@Data
	public static class SelectData {

		private String key;

		private String value;
	}

	public static enum FieldType {
		TEXT, PASSWORD, MULTI_TEXT, SELECT, CHECKBOX, REDIO, DATE, DATETIME, CERTIFICATE, INVENTORY_SCRIPT, VARIABLE
	}
}
