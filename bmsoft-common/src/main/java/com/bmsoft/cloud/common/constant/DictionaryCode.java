package com.bmsoft.cloud.common.constant;

/**
 * 存放系统中常用的类型
 *
 * @author bmsoft
 * @date 2019/07/26
 */
public class DictionaryCode {
    /**
     * 职位状态
     */
    public static final String POSITION_STATUS = "POSITION_STATUS";
    /**
     * 民族
     */
    public static final String NATION = "NATION";
    /**
     * 学历
     */
    public static final String EDUCATION = "EDUCATION";
    public static final String[] ALL = new String[]{
            DictionaryCode.EDUCATION, DictionaryCode.NATION, DictionaryCode.POSITION_STATUS
    };

    private DictionaryCode() {
    }

}
