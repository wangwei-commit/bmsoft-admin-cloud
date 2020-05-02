//package com.bmsoft.cloud.area2;
//
//import com.bmsoft.cloud.authority.entity.common.Area;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//public class CityParserDecorator implements ICityParser {
//
//    private ICityParser cityParser;
//
//    public CityParserDecorator(ICityParser cityParser) {
//        this.cityParser = cityParser;
//    }
//
//    public List<Area> parseProvinces(String url) {
//        return this.cityParser.parseProvinces(url);
//    }
//
//}
