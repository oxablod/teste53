package com.teste;

import org.hibernate.collection.internal.PersistentBag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		Converter<PersistentBag, List<Object>> converter = context -> {
			PersistentBag source = context.getSource();
			List<Object> target = new ArrayList<>(source.size());
			target.addAll(source);
			return target;
		};

		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(converter);

		return mapper;
	}
}
