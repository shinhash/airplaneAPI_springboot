package dev.hash.airplaneAPI.airplane.api.myinfo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hash.airplaneAPI.airplane.api.myinfo.mapper.AirplaneAPIMyInfoMapper;
import dev.hash.airplaneAPI.airplane.api.myinfo.service.AirplaneAPIMyInfoService;
import dev.hash.airplaneAPI.airplane.comm.validate.AirplaneAPIValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service(value="airplaneAPIMyInfoService")
public class AirplaneAPIMyInfoServiceImpl implements AirplaneAPIMyInfoService {
	/**
	 * AirplaneAPIValidation.airplaneValidation return CODE information
	 * 
	 * [유효성 검사]
	 * APVR-000 : 유효성 검사 체크 성공
	 * APVR-100 : 유효성 검사 체크 실패
	 * 
	 * [로그인 토큰 인증]
	 * APAT-000 : 로그인 토큰 인증 성공
	 * APAT-100 : 로그인 토큰 인증 실패
	 * APAT-200 : 로그인 토큰 undefined
	 * 
	 * [내정보 조회]
	 * APMI-000 : 내정보 조회 성공
	 * APMI-100 : 내정보 조회 실패
	 * 
	 * [내정보 수정]
	 * APMU-000 : 내정보 수정 성공
	 * APMU-100 : 내정보 수정 실패
	 * APMU-200 : 내정보 수정 실패(본인계정이 아님)
	 * 
	 */
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AirplaneAPIValidation airplaneAPIValidation;
	
	@Autowired
	private AirplaneAPIMyInfoMapper airplaneAPIMyInfoMapper;

	@Override
	public Map<String, Object> getMyInfo(Map<String, Object> receiveJson, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LOGGER.info("receiveJson : {}", receiveJson);
		
		Map<String, String> commCdInfo = new HashMap<String, String>();
		commCdInfo.put("commCd", "VALID");
		commCdInfo.put("commDetailCd", "MYIF-001");
		
		Map<String, Object> validateResult = airplaneAPIValidation.airplaneValidation(receiveJson, commCdInfo);
		
		if(validateResult.get("resultCode").equals("APVR-000")) {
			HttpSession session = request.getSession();
			String accessToken = (String) receiveJson.get("accessToken");
			String accessTokenSession = (String) session.getAttribute("accessTokenSession");
			if(accessTokenSession != null && accessTokenSession.equals(accessToken)) {
				String accessUserId = (String) session.getAttribute("accessUserId");
				LOGGER.info("accessUserId : {}", accessUserId);
				try {
					Map<String, Object> userInfoResult = airplaneAPIMyInfoMapper.getMyInfo(accessUserId);
					LOGGER.info("userInfoResult : {}", userInfoResult);
					if(userInfoResult != null) {
						resultMap.put("resultCode", "APMI-000");
						resultMap.put("resultMessage", "[myinfo select success]");
						resultMap.put("resultData", userInfoResult);
					}else {
						resultMap.put("resultCode", "APMI-100");
						resultMap.put("resultMessage", "[myinfo is null]");
					}
				}catch (Exception e) {
					resultMap.put("resultCode", "APMI-100");
					resultMap.put("resultMessage", "[myinfo select fail]");
				}
			}else {
				resultMap.put("resultCode", "APAT-100");
				resultMap.put("resultMessage", "[signin access token auth check fail]");
			}
			
		}else {
			resultMap.put("resultCode", validateResult.get("resultCode"));
			resultMap.put("resultMessage", validateResult.get("resultMessage"));
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateMyInfo(Map<String, Object> receiveJson, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LOGGER.info("receiveJson : {}", receiveJson);
		
		Map<String, String> commCdInfo = new HashMap<String, String>();
		commCdInfo.put("commCd", "VALID");
		commCdInfo.put("commDetailCd", "MYIF-002");
		
		Map<String, Object> validateResult = airplaneAPIValidation.airplaneValidation(receiveJson, commCdInfo);
		
		if(validateResult.get("resultCode").equals("APVR-000")) {
			HttpSession session = request.getSession();
			String accessToken = (String) receiveJson.get("accessToken");
			String accessTokenSession = (String) session.getAttribute("accessTokenSession");
			if(accessTokenSession.equals(accessToken)) {
				String accessUserId = (String) session.getAttribute("accessUserId");
				if(accessUserId.equals(receiveJson.get("userId"))) {
					try {
						airplaneAPIMyInfoMapper.updateMyInfo(receiveJson);
						resultMap.put("resultCode", "APMU-000");
						resultMap.put("resultMessage", "[myinfo update success]");
					}catch (Exception e) {
						resultMap.put("resultCode", "APMU-100");
						resultMap.put("resultMessage", "[myinfo update fail]");
					}
				}else {
					resultMap.put("resultCode", "APMU-200");
					resultMap.put("resultMessage", "[this update infomation is not your's]");
				}
			}
		}else {
			resultMap.put("resultCode", validateResult.get("resultCode"));
			resultMap.put("resultMessage", validateResult.get("resultMessage"));
		}
		return resultMap;
	}

	
}
