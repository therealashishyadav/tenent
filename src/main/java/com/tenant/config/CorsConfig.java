//package com.tenant.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//				.allowedOrigins("http://localhost:4200", "https://cribup.vercel.app",
//						"https://apigateway-x0ku.onrender.com")
//				.allowedOriginPatterns("https://cribup-*.vercel.app", "https://*-therealashishyadav1.vercel.app")
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS").allowedHeaders("*")
//				.allowCredentials(true);
//	}
//}