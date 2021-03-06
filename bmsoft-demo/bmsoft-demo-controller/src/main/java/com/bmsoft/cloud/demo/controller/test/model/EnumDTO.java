package com.bmsoft.cloud.demo.controller.test.model;

import com.bmsoft.cloud.common.enums.HttpMethod;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 枚举测试DTO
 *
 * @author bmsoft
 * @date 2019/07/30
 */
@Data
@ToString
public class EnumDTO implements Serializable {
    private Long id;
    private String name;
    private HttpMethod httpMethod;

    private List<Producttt> list;

    public void testEx() throws Exception {
        throw new Exception("eeeeee");
    }
}
