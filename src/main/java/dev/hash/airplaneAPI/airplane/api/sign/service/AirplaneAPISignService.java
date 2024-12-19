package dev.hash.airplaneAPI.airplane.api.sign.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface AirplaneAPISignService {
	
	public Map<String, Object> signIn(Map<String, Object> receiveJson, HttpServletRequest request) throws Exception;
	
	public Map<String, Object> signIdCheck(Map<String, Object> receiveJson) throws Exception;

	public Map<String, Object> signUp(Map<String, Object> receiveJson) throws Exception;
	
	public Map<String, Object> signOut(Map<String, Object> receiveJson) throws Exception;

	public Map<String, Object> accessTokenCheck(Map<String, Object> receiveJson) throws Exception;
}
