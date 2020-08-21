package com.bmsoft.cloud.work.handler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.bmsoft.cloud.model.RemoteData;

@SuppressWarnings("rawtypes")
public class RemoteDataListHandler extends AbstractJsonTypeHandler<List<RemoteData>> {

	@SuppressWarnings("unchecked")
	@Override
	protected List<RemoteData> parse(String json) {
		List<Object> keys = JSON.parseObject(json, List.class);
		return Optional.ofNullable(keys)
				.map(list -> list.stream().map(key -> new RemoteData(key)).collect(Collectors.toList())).orElse(null);
	}

	@Override
	protected String toJson(List<RemoteData> obj) {
		return JSON.toJSONString(Optional.ofNullable(obj)
				.map(list -> list.stream().map(data -> data.getKey()).collect(Collectors.toList())).orElse(null),
				SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty);
	}

}
