package com.example.demo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConvertToSimpleObj implements Converter<String, SimpleObj> {
	@Override
	public SimpleObj convert(String source) {
		return new SimpleObj(source + " convert");
	}

}
