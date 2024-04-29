package com.example.pigonair.domain.seat.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.pigonair.domain.seat.dto.AllowedUserResponse;
import com.example.pigonair.domain.seat.dto.SeatResponseDto;
import com.example.pigonair.domain.seat.service.SeatService;
import com.example.pigonair.global.config.jmeter.JmeterService;
import com.example.pigonair.global.config.security.UserDetailsImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {

	private final SeatService seatService;
	private final JmeterService jmeterService;
	private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

	RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/{flightId}")
	public String getSeatingChart(
		@RequestParam(name = "queue", defaultValue = "default") String queue,
		@RequestParam(name = "wait_token",defaultValue = "default") String waitToken,
		@PathVariable Long flightId, Model model,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		HttpServletRequest request,
		HttpServletResponse response1) {

		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

		String waitQueueKey = "user-queue-default-token";
		if(!waitToken.equals("default")) {
			waitQueueAddCookie(response1, waitQueueKey, waitToken);
		}

		Cookie[] cookies = request.getCookies();
		// Optional<Cookie> cookie1 = Arrays.stream(cookies)
		// 	.filter(i -> i.getName().equalsIgnoreCase("Authorization"))
		// 	.findFirst();
		// String token1 = cookie1.orElse(new Cookie("Authorization", "")).getValue();

		if (threadMXBean.getThreadCount() > 1) {
			ResponseEntity<AllowedUserResponse> response = waitSystem(request, queue, userDetails.getUser().getId());
			// 허용 불가 상태
			if (response.getBody() == null || !response.getBody().allowed()) {
				// 대기 웹페이지로 리다이렉트
				return "redirect:http://13.124.86.199:9010/waiting-room?user_id=%d&redirect_url=%s".formatted(
					userDetails.getUser().getId(), "https://pigonair-dev.shop/seat/%d".formatted(flightId));
			} //"https://pigonair-dev.shop/seat/%d".formatted(flightId)
		}
		List<SeatResponseDto> seatsDto = seatService.getSeatingChart(flightId);
		jmeterService.setTransactionNameBasedOnJMeterTag(request);
		model.addAttribute("seats", seatsDto);

		return "seats/seatList";
	}

	private void waitQueueAddCookie(HttpServletResponse response,String waitQueueKey, String waitToken) {
		Cookie myCookie = new Cookie(waitQueueKey, waitToken);
		myCookie.setMaxAge(300);
		myCookie.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
		response.addCookie(myCookie);
	}
	// @GetMapping("/redirect")
	// public String myRedirectMethod(RedirectAttributes attributes) {
	// 	// 헤더 값 추가
	// 	attributes.addFlashAttribute("customHeader", "customValue");
	// 	// 리디렉션할 URL 반환
	// 	return "redirect:/new-url";
	// } //13.124.86.199:9010

	public ResponseEntity<AllowedUserResponse> waitSystem(HttpServletRequest request, String queue, Long userId) {

		Cookie[] cookies = request.getCookies();
		String cookieName = "user-queue-%s-token".formatted(queue);

		Optional<Cookie> cookie1 = Arrays.stream(cookies)
			.filter(i -> i.getName().equalsIgnoreCase("Authorization"))
			.findFirst();
		String token1 = cookie1.orElse(new Cookie("Authorization", "")).getValue();

		String token = "";
		if (cookies != null) {
			Optional<Cookie> cookie = Arrays.stream(cookies)
				.filter(i -> i.getName().equalsIgnoreCase(cookieName))
				.findFirst();
			token = cookie.orElse(new Cookie(cookieName, "")).getValue();
		}

		URI uri = UriComponentsBuilder
			.fromUriString("http://13.124.86.199:9010")
			.path("/api/v1/queue/allowed")
			.queryParam("queue", queue)
			.queryParam("user_id", userId)
			.queryParam("token", token)
			.encode()
			.build()
			.toUri();
		System.out.println(uri);
		ResponseEntity<AllowedUserResponse> response = restTemplate.getForEntity(uri, AllowedUserResponse.class);

		return response;
	}

}
