//package com.bmsoft.cloud.msgs.api;
//
//import com.bmsoft.cloud.base.R;
//import com.bmsoft.cloud.msgs.api.fallback.MsgsGeneralApiFallback;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.Map;
//
///**
// * 通用API
// *
// * @author bmsoft
// * @date 2019/07/26
// */
//@FeignClient(name = "${bmsoft.feign.msgs-server:bmsoft-msgs-server}", fallback = MsgsGeneralApiFallback.class)
//public interface MsgsGeneralApi {
//    /**
//     * 查询系统中常用的枚举类型等
//     *
//     * @return
//     */
//    @GetMapping("/enums")
//    R<Map<String, Map<String, String>>> enums();
//}
