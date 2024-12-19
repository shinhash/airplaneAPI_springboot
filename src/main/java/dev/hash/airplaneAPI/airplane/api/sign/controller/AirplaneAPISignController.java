package dev.hash.airplaneAPI.airplane.api.sign.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.hash.airplaneAPI.airplane.api.sign.service.AirplaneAPISignService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class AirplaneAPISignController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Resource(name="airplaneAPISignService")
	private AirplaneAPISignService airplaneAPISignService;
	
	@PostMapping(value="/airplane/accessTokenChk")
	public Map<String, Object> airplaneAPIAccessTokenCheck(@RequestBody Map<String, Object> receiveJson, HttpServletRequest request) throws Exception {
		LOGGER.info("receiveJson : {}", receiveJson);
		HttpSession session = request.getSession();
		LOGGER.info("accessTokenChk session : {}", session.getAttribute("accessTokenSession"));
		receiveJson.put("accessTokenSession", session.getAttribute("accessTokenSession"));
		Map<String, Object> accessTokenCheckResult = airplaneAPISignService.accessTokenCheck(receiveJson);
		return accessTokenCheckResult;
	}
	
	@PostMapping(value="/airplane/signin")
	public Map<String, Object> airplaneAPISignIn(@RequestBody Map<String, Object> receiveJson, HttpServletRequest request) throws Exception {
		LOGGER.info("receiveJson : {}", receiveJson);
		Map<String, Object> signInResult = airplaneAPISignService.signIn(receiveJson, request);
		return signInResult;
	}
	
	@PostMapping(value="/airplane/signidchk")
	public Map<String, Object> airplaneAPISignIdChk(@RequestBody Map<String, Object> receiveJson) throws Exception {
		LOGGER.info("receiveJson : {}", receiveJson);
		Map<String, Object> signUpResult = airplaneAPISignService.signIdCheck(receiveJson);
		return signUpResult;
	}
	
	@PostMapping(value="/airplane/signup")
	public Map<String, Object> airplaneAPISignUp(@RequestBody Map<String, Object> receiveJson) throws Exception {
		LOGGER.info("receiveJson : {}", receiveJson);
		Map<String, Object> signUpResult = airplaneAPISignService.signUp(receiveJson);
		return signUpResult;
	}
	
	@PostMapping(value="/airplane/signout")
	public Map<String, Object> airplaneAPISignOut(@RequestBody Map<String, Object> receiveJson) throws Exception {
		LOGGER.info("receiveJson : {}", receiveJson);
		Map<String, Object> signUpResult = airplaneAPISignService.signOut(receiveJson);
		return signUpResult;
	}
	
	
	@GetMapping(value="/dev/sessionChk")
	public void devSessionChk(HttpServletRequest request) {
		HttpSession session = request.getSession();
		LOGGER.info("devSessionChk session : {}", session.getAttribute("accessTokenSession"));
	}

}
