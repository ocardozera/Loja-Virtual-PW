package com.lojavirtualpw.lojavirtualpw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LojavirtualpwApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojavirtualpwApplication.class, args);

		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
