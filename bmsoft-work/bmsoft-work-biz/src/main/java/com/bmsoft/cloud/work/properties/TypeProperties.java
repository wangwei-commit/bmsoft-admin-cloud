package com.bmsoft.cloud.work.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import lombok.Data;

@Data
@RefreshScope
@ConfigurationProperties("bmsoft.work.type")
public class TypeProperties {

	private List<Type> inventorySource;
	
	private List<Type> certificateType;
	
	@Data
	public static class Type {
		
		private String key;
		
		private String display;
		
		private List<TypeField> fields;
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
