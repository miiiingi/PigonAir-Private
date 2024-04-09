package com.example.pigonair.global.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.pigonair.global.config.security.jwt.JwtAuthenticationFilter;
import com.example.pigonair.global.config.security.jwt.JwtAuthorizationFilter;
import com.example.pigonair.global.config.security.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private static final String[] APP_WHITE_LIST = {
		"/signup",
		"/login-page",
		"/",
		"/apm/**",
		"/actuator/**",
		"/flight/**",
		"/swagger/**",
		"/monitoring/grafana/**",
		"/monitoring/prometheus/**",

	};

	private static final String[] SWAGGER_URL_ARRAY = {
		/* swagger v2 */
		"/v2/api-docs",
		"/swagger-resources",
		"/swagger-resources/**",
		"/configuration/ui",
		"/configuration/security",
		"/swagger-ui.html",
		"/webjars/**",
		/* swagger v3 */
		"/v3/api-docs/**",
		"/swagger-ui/**"
	};

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	public WebSecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl adminDetailsService,
		AuthenticationConfiguration authenticationConfiguration, CustomAccessDeniedHandler customAccessDeniedHandler,
		CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = adminDetailsService;
		this.authenticationConfiguration = authenticationConfiguration;
		this.customAccessDeniedHandler = customAccessDeniedHandler;
		this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
		filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		return filter;
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		return (request, response, exception) -> {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Unauthorized: " + exception.getMessage());
		};
	}

	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return (request, response, authentication) -> {
			// 로그인 성공 후의 동작을 정의
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("Login successful");
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowCredentials(true); // 내부 요청에 대한 응답으로 쿠키를 포함시킬지 여부
			config.addAllowedOrigin("https://pigonair.shop"); // 모든 도메인에서의 요청을 허용
			config.addAllowedHeader("*"); // 모든 헤더에 대한 요청을 허용
			config.addAllowedMethod("*"); // 모든 HTTP 메소드 요청을 허용
			return config;
		}));

		http.sessionManagement((sessionManagement) ->
			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);

		http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers(APP_WHITE_LIST).permitAll()
				.requestMatchers(SWAGGER_URL_ARRAY).permitAll()
				.anyRequest().authenticated()
		);
		http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class)
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling((exceptionConfig) -> exceptionConfig
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler)
			);

		http.logout((logout) ->
			logout
				.logoutUrl("/logout")
				.addLogoutHandler((request, response, authentication) -> {
					// 사실 굳이 내가 세션 무효화하지 않아도 됨.
					// LogoutFilter가 내부적으로 해줌.
					HttpSession session = request.getSession();
					if (session != null) {
						session.invalidate();
					}
				})  // 로그아웃 핸들러 추가
				.logoutSuccessHandler((request, response, authentication) -> {
					response.setStatus(HttpServletResponse.SC_OK); // 응답 상태 변경
				}) // 로그아웃 성공 핸들러
				.deleteCookies(JwtUtil.AUTHORIZATION_HEADER)); // 로그아웃 후 삭제할 쿠키 지정

		return http.build();
	}

}
