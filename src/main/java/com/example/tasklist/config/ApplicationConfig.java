package com.example.tasklist.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.tasklist.service.UserService;
import com.example.tasklist.web.security.JwtTokenFilter;
import com.example.tasklist.web.security.JwtTokenProvider;
import com.example.tasklist.web.security.JwtUserDetailService;
import com.example.tasklist.web.security.expression.CustomSecurityExceptionHandler;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final ApplicationContext applicationContext;
	
	@Lazy
	public ApplicationConfig(ApplicationContext applicationContext, JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.applicationContext = applicationContext;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public MethodSecurityExpressionHandler expressionHandler() {
		DefaultMethodSecurityExpressionHandler handler = new CustomSecurityExceptionHandler();
		handler.setApplicationContext(applicationContext);
		return handler;
	}
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(
						new Components()
						.addSecuritySchemes("bearerAuth", 
								new SecurityScheme()
									.type(SecurityScheme.Type.HTTP)
									.scheme("bearer")
									.bearerFormat("JWT")
						)
				)
				.info(new Info()
                		.title("Task list API")
                		.description("Demo spring boot application")
                		.version("1.0")
                );
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.
			csrf().disable()
			.cors()
			.and()
			.httpBasic().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint((request, response, authException) -> {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write("unathorised");
			})
			.accessDeniedHandler((request, response, accessDeniedException) -> {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write("unathorised");
			})
			.and()
			.authorizeHttpRequests()
			.requestMatchers("/api/v1/auth/**").permitAll()
			.requestMatchers("/swagger-ui/**").permitAll()
			.requestMatchers("/v3/api-docs/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.anonymous().disable()
			.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
}
