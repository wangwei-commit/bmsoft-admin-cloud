package com.bmsoft.cloud.work.entity;

import lombok.Data;

@Data
public class SourceEntity {
        private String name;
        private Integer type;
        private String code;
        private String codeType;
        private String envCode;
        private String envType;
}
