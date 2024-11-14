package com.example.qna_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Auditing 허용
@SpringBootApplication
public class QnaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(QnaBackendApplication.class, args);
	}

}
