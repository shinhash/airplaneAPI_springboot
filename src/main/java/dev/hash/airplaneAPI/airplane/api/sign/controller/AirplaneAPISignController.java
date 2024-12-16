package dev.hash.airplaneAPI.airplane.api.sign.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.hash.airplaneAPI.airplane.api.sign.service.AirplaneAPISignService;
import jakarta.annotation.Resource;

@RestController
public class AirplaneAPISignController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Resource(name="airplaneAPISignService")
	private AirplaneAPISignService airplaneAPISignService;
	
	@PostMapping(value="/airplane/signin")
	public Map<String, Object> airplaneAPISignIn(@RequestBody Map<String, Object> receiveJson) throws Exception {
		LOGGER.info("receiveJson : {}", receiveJson);
		Map<String, Object> signInResult = airplaneAPISignService.signIn(receiveJson);
		return signInResult;
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

}
