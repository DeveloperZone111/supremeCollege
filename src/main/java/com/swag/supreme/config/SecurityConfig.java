package com.swag.supreme.config;
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.AuthenticationProvider; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; 
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.swag.supreme.filter.JwtAuthFilter;
import com.swag.supreme.service.UserInfoService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 

	@Autowired
	private JwtAuthFilter authFilter; 

	// User Creation 
	@Bean
	public UserDetailsService userDetailsService() { 
		return new UserInfoService(); 
	} 

	/*
	 * // Configuring HttpSecurity
	 * 
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 * throws Exception { return http.csrf().disable() .authorizeHttpRequests()
	 * .requestMatchers( "/auth/sinup", "/auth/sinin").permitAll() .and()
	 * .authorizeHttpRequests().requestMatchers("/auth/**").authenticated() .and()
	 * .authorizeHttpRequests().requestMatchers("/auth/admin/**").authenticated()
	 * .and() .sessionManagement()
	 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS) .and()
	 * .authenticationProvider(authenticationProvider())
	 * .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
	 * .build(); }
	 */
	
	
	 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
   
        http.cors().and().csrf().disable().	
		authorizeRequests().requestMatchers("/auth/sinup", "/auth/sinin").permitAll()
		.requestMatchers(HttpHeaders.ALLOW).permitAll()
//		.requestMatchers("/api/v1/**").authenticated()
		.anyRequest().authenticated().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		and()
		.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling()
	 
		.and().headers()
		.addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1,mode=block"))
//		.addHeaderWriter(new StaticHeadersWriter("Referrer-Policy", "strict-origin-when-cross-origin"))
		.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin","*"))
		.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept"))
		.addHeaderWriter(new StaticHeadersWriter("X-Frame-Options", "content=deny"))
		.addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"))
		.addHeaderWriter(new StaticHeadersWriter("Strict-Transport-Security", "max-age=31536000"))
		.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, GET","OPTIONS"))
		.addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy",
				"default-src 'self';" +
					"script-src 'self' 'unsafe-inline';" +
					"style-src 'self' 'unsafe-inline';" +
					"img-src 'none';" +
					"connect-src 'self';" +
					"font-src 'self';" +
					"object-src 'none';" +
					"base-uri 'self';" +
					"form-action 'self';" +
					"frame-ancestors 'none';" +
					"media-src 'none';" +
					"sandbox allow-forms allow-same-origin allow-scripts;" +
					"camera 'none';" +
					"geolocation 'none';" +
					"microphone 'none';"))
		.frameOptions()
		.sameOrigin();
       return http.build();
    
    }
    
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200") // Replace with your Angular app URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                        .allowedHeaders("*") // Allowed request headers (you can customize this based on your requirements)
                        .allowCredentials(true);
            }
        };
    }

	// Password Encoding 
	@Bean
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	} 

	@Bean
	public AuthenticationProvider authenticationProvider() { 
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
		authenticationProvider.setUserDetailsService(userDetailsService()); 
		authenticationProvider.setPasswordEncoder(passwordEncoder()); 
		return authenticationProvider; 
	} 

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
		return config.getAuthenticationManager(); 
	} 


} 
