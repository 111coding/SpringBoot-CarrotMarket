package com.example.carrotmarket;

import com.example.carrotmarket.modules.product.domain.entity.ProductCategory;
import com.example.carrotmarket.modules.product.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class CarrotmarketApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarrotmarketApplication.class, args);
	}

	@Autowired
	ProductCategoryRepository productCategoryRepository;
	@Override
	public void run(String... args) throws Exception {
		if(productCategoryRepository.findAll().isEmpty()){
			List<String> categories = List.of("디지털기기", "생활가전", "가구/인테리어", "유아동", "유아도서", "생활/가공식품", "스포츠/레저", "여성잡화", "여성의류", "남성패션/잡화", "게임/취미", "뷰티/미용", "반려동물용품", "도서/티켓/음반", "식물", "기타 중고물품", "삽니다");

			List<ProductCategory> productCategories = categories.stream()
					.map(s -> ProductCategory.builder().category(s).build())
					.collect(Collectors.toList());
			productCategoryRepository.saveAll(productCategories);
		}

	}

}
