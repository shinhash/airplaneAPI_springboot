package dev.hash.airplaneAPI.airplane.api.myinfo.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface AirplaneAPIMyInfoService {

	public Map<String, Object> getMyInfo(Map<String, Object> receiveJson, HttpServletRequest request) throws Exception;
	
	public Map<String, Object> updateMyInfo(Map<String, Object> receiveJson, HttpServletRequest request) throws Exception;
}
