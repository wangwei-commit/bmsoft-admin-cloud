package com.bmsoft.cloud;

import com.bmsoft.cloud.context.BaseContextHandler;
import com.bmsoft.cloud.demo.dao.CCommonAreaMapper;
import com.bmsoft.cloud.demo.entity.CCommonArea;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is a Description
 *
 * @author bmsoft
 * @date 2019/08/20
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class TestArea {
    @Autowired
    private CCommonAreaMapper mapper;

    @Test
    public void test() {

        Long id = 585823974982680865L;
        CCommonArea cCommonArea = mapper.selectById(id);
        System.out.println(cCommonArea);

        BaseContextHandler.setName("bmsoft_authority_dev");
        cCommonArea = mapper.selectById(id);
        System.out.println(cCommonArea);

    }

}
