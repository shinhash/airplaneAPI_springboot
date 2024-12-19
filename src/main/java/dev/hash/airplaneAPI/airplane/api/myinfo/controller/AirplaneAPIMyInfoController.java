package dev.hash.airplaneAPI.airplane.api.myinfo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.hash.airplaneAPI.airplane.api.myinfo.service.AirplaneAPIMyInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AirplaneAPIMyInfoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Resource(name="airplaneAPIMyInfoService")
	private AirplaneAPIMyInfoService airplaneAPIMyInfoService;
	
	
	@PostMapping(value="/airplane/myinfo/detail")
	public Map<String, Object> airplaneAPIMyInfoDetail(@RequestBody Map<String, Object> receiveJson, HttpServletRequest request) throws Exception {
		LOGGER.info("receiveJson : {}", receiveJson);
		Map<String, Object> getMyInfoDetailResult = airplaneAPIMyInfoService.getMyInfo(receiveJson, request);
		return getMyInfoDetailResult;
	}
	
	@PostMapping(value="/airplane/myinfo/update")
	public Map<String, Object> airplaneAPIMyInfoUpdate(@RequestBody Map<String, Object> receiveJson, HttpServletRequest request) throws Exception {
		LOGGER.info("receiveJson : {}", receiveJson);
		Map<String, Object> updateMyInfoDetailResult = airplaneAPIMyInfoService.updateMyInfo(receiveJson, request);
		return updateMyInfoDetailResult;
	}

}
