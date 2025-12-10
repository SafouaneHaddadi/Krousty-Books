package com.biblio.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String hash1 = encoder.encode("...");
        //System.out.println(hash1);
	}

	/*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:8082")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    } */

}
