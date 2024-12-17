package dev.hash.airplaneAPI.airplane.api.sign.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hash.airplaneAPI.airplane.api.sign.mapper.AirplaneAPISignMapper;
import dev.hash.airplaneAPI.airplane.api.sign.service.AirplaneAPISignService;
import dev.hash.airplaneAPI.airplane.comm.utils.SHA256Util;
import dev.hash.airplaneAPI.airplane.comm.validate.AirplaneAPIValidation;

@Service(value="airplaneAPISignService")
public class AirplaneAPISignServiceImpl implements AirplaneAPISignService {
	/**
	 * AirplaneAPIValidation.airplaneValidation return CODE information
	 * 
	 * [유효성 검사]
	 * APVR-000 : 유효성 검사 체크 성공
	 * APVR-100 : 유효성 검사 체크 실패
	 * 
	 * [로그인]
	 * APSI-000 : 로그인 성공
	 * APSI-100 : 로그인 실패(비밀번호 틀림)
	 * APSI-200 : 로그인 실패(계정 없음)
	 * 
	 * [아이디 중복체크]
	 * APIC-000 : 아이디 중복 미발생
	 * APIC-100 : 아이디 중복 발생
	 * 
	 * [회원가입]
	 * APSU-000 : 회원가입 성공
	 * APSU-100 : 회원가입 실패
	 * 
	 */
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AirplaneAPIValidation airplaneAPIValidation;
	
	@Autowired
	private AirplaneAPISignMapper airplaneAPISignMapper;

	@Override
	public Map<String, Object> signIn(Map<String, Object> receiveJson) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LOGGER.info("receiveJson : {}", receiveJson);
		
		Map<String, String> commCdInfo = new HashMap<String, String>();
		commCdInfo.put("commCd", "VALID");
		commCdInfo.put("commDetailCd", "SIGN-001");
		
		Map<String, Object> validateResult = airplaneAPIValidation.airplaneValidation(receiveJson, commCdInfo);
		
		if(validateResult.get("resultCode").equals("APVR-000")) {
			
			Map<String, Object> userInfo = airplaneAPISignMapper.getUserInfo((String) receiveJson.get("userId"));
			LOGGER.info("userInfo : {}", userInfo);
			
			if(userInfo != null) {
				String inputUserPw = SHA256Util.getSHA256Hash((String) receiveJson.get("userPw"));
				LOGGER.info("inputUserPw : {}", inputUserPw);
				
				if(inputUserPw.equals((String) userInfo.get("userPw"))) {
					resultMap.put("resultCode", "APSI-000");
					resultMap.put("resultMessage", "[signin success]");
				}else {
					resultMap.put("resultCode", "APSI-100");
					resultMap.put("resultMessage", "[user password is different]");
				}
			}else {
				resultMap.put("resultCode", "APSI-200");
				resultMap.put("resultMessage", "[user info is null]");
			}
			
		}else {
			resultMap.put("resultCode", validateResult.get("resultCode"));
			resultMap.put("resultMessage", validateResult.get("resultMessage"));
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> signIdCheck(Map<String, Object> receiveJson) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LOGGER.info("receiveJson : {}", receiveJson);
		
		Map<String, String> commCdInfo = new HashMap<String, String>();
		commCdInfo.put("commCd", "VALID");
		commCdInfo.put("commDetailCd", "SIGN-003");
		
		Map<String, Object> validateResult = airplaneAPIValidation.airplaneValidation(receiveJson, commCdInfo);
		
		if(validateResult.get("resultCode").equals("APVR-000")) {
			
			int idCheckCnt = airplaneAPISignMapper.getIdCheckCnt((String) receiveJson.get("userId"));
			if(idCheckCnt == 0) {
				resultMap.put("resultCode", "APIC-000");
				resultMap.put("resultMessage", "[id check is success]");
			}else {
				resultMap.put("resultCode", "APIC-100");
				resultMap.put("resultMessage", "[id is duplicate]");
			}
			
		}else {
			resultMap.put("resultCode", validateResult.get("resultCode"));
			resultMap.put("resultMessage", validateResult.get("resultMessage"));
		}
		return resultMap;
	}
	

	@Override
	public Map<String, Object> signUp(Map<String, Object> receiveJson) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LOGGER.info("receiveJson : {}", receiveJson);
		
		Map<String, String> commCdInfo = new HashMap<String, String>();
		commCdInfo.put("commCd", "VALID");
		commCdInfo.put("commDetailCd", "SIGN-002");
		
		Map<String, Object> validateResult = airplaneAPIValidation.airplaneValidation(receiveJson, commCdInfo);
		
		if(validateResult.get("resultCode").equals("APVR-000")) {
			
			try {
				int idCheckCnt = airplaneAPISignMapper.getIdCheckCnt((String) receiveJson.get("userId"));
				if(idCheckCnt == 0) {
					airplaneAPISignMapper.insertUserInfo(receiveJson);
					resultMap.put("resultCode", "APSU-000");
					resultMap.put("resultMessage", "[signup is success]");
				}else {
					resultMap.put("resultCode", "APIC-100");
					resultMap.put("resultMessage", "[id is duplicate]");
				}
			}catch (Exception e) {
				LOGGER.error("error : {}", e);
				resultMap.put("resultCode", "APSU-100");
				resultMap.put("resultMessage", "[signup is fail]");
			}
			
		}else {
			resultMap.put("resultCode", validateResult.get("resultCode"));
			resultMap.put("resultMessage", validateResult.get("resultMessage"));
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> signOut(Map<String, Object> receiveJson) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LOGGER.info("receiveJson : {}", receiveJson);
		
		Map<String, String> commCdInfo = new HashMap<String, String>();
		commCdInfo.put("commCd", "VALID");
		commCdInfo.put("commDetailCd", "SIGN-003");
		
		Map<String, Object> validateResult = airplaneAPIValidation.airplaneValidation(receiveJson, commCdInfo);
		
		if(validateResult.get("resultCode").equals("APVR-000")) {
			
		}else {
			resultMap.put("resultCode", validateResult.get("resultCode"));
			resultMap.put("resultMessage", validateResult.get("resultMessage"));
		}
		return resultMap;
	}
	
}
