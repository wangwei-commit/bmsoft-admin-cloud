package com.bmsoft.cloud.oauth.api;

import com.bmsoft.cloud.oauth.api.hystrix.DictionaryItemApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 数据字典API
 *
 * @author bmsoft
 * @date 2019/07/26
 */
@FeignClient(name = "${bmsoft.feign.oauth-server:bmsoft-oauth-server}", path = "dictionaryItem",
        qualifier = "dictionaryItemApi", fallback = DictionaryItemApiFallback.class)
public interface DictionaryItemApi {

    /**
     * 根据 code 查询字典
     *
     * @param codes
     * @return
     */
    @GetMapping("/findDictionaryItem")
    Map<Serializable, Object> findDictionaryItem(@RequestParam(value = "codes") Set<Serializable> codes);
}
