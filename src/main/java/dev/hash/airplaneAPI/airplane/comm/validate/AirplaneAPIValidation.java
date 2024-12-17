package dev.hash.airplaneAPI.airplane.comm.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.hash.airplaneAPI.airplane.comm.mapper.AirplaneAPICommMapper;

@Component
public class AirplaneAPIValidation {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AirplaneAPICommMapper airplaneAPICommMapper;
	
	/**
	 * validation check method
	 * @param data
	 * @param serviceType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> airplaneValidation(Map<String, Object> data, Map<String, String> commCdInfo) throws Exception {
		Map<String, Object> validateResult = new HashMap<String, Object>();
		
		String valRstCd = "SUCCESS";
		StringBuffer validateResultMessage = new StringBuffer();
		validateResultMessage.append("[");
		
		List<Map<String, Object>> validateValueList = airplaneAPICommMapper.validateList(commCdInfo);
		LOGGER.info("validateValueList : {}", validateValueList);
		for(Map<String, Object> validateMap : validateValueList) {
			String commDetailCn = (String) validateMap.get("commDetailCn");
			for(String key : data.keySet()) {
				if(commDetailCn.equals(key) && validateMap.get("useYn").equals("Y")) {					
					if(data.get(key).equals("")) {
						valRstCd = "FAIL";
						validateResultMessage.append(key + " value is empty, ");
					}
				}
			}
			if(!new ArrayList<String>(data.keySet()).contains(commDetailCn)) {
				valRstCd = "FAIL";
				validateResultMessage.append(commDetailCn + " key is undefined, ");
			}
		}
		
		if(valRstCd.equals("SUCCESS")) validateResult.put("resultCode", "APVR-000");
		else if(valRstCd.equals("FAIL")) validateResult.put("resultCode", "APVR-100");
		
		validateResultMessage.append("]");
		validateResult.put("resultMessage", validateResultMessage.toString());
		return validateResult;
	}
}
