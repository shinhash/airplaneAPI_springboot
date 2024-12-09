package dev.hash.airplaneAPI.airplane.api.service.impl;

import dev.hash.airplaneAPI.airplane.api.mapper.AirplaneAPIMapper;
import dev.hash.airplaneAPI.airplane.api.service.AirplaneAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
@Service(value="airplaneAPIService")
public class AirplaneAPIServiceImpl implements AirplaneAPIService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private AirplaneAPIMapper airplaneAPIMapper;

    @Override
    public List<Map<String, Object>> getAirplaneList() throws Exception {
        return airplaneAPIMapper.getAirplaneList();
    }

    @Override
    public Map<String, String> saveAirplaneList(Map<String, Object> apiResult) throws Exception {
        Map<String, String> apiDataSaveRst = new HashMap<String, String>();

        Map<String, Object> headerMap = ((Map<String, Object>) apiResult.get("header"));
        Map<String, Object> bodyMap = ((Map<String, Object>) apiResult.get("body"));

        LOGGER.info("headerMap : {}", headerMap);
        LOGGER.info("bodyMap : {}", bodyMap);

        String apiResultCode = headerMap.get("resultCode").toString();
        String apiResultMsg = headerMap.get("resultMsg").toString();

        List<Map<String, Object>> apiResultItemList = (List<Map<String, Object>>) bodyMap.get("items");

        if(apiResultCode.equals("00")) {
            try {
                for(Map<String, Object> apiResultItemInfo : apiResultItemList) {
                    int airPlainCtn = airplaneAPIMapper.checkAirplaneInfo(apiResultItemInfo);
                    LOGGER.info("airPlainCtn : {}", airPlainCtn);
                    if(airPlainCtn == 0) {
                        airplaneAPIMapper.saveAirplaneInfo(apiResultItemInfo);
                    }
                }
                apiDataSaveRst.put("result", "SUCCESS");
            }catch(Exception e) {
                apiDataSaveRst.put("result", "ERROR");
            }
        }else {
            apiDataSaveRst.put("result", "FAIL");
        }
        apiDataSaveRst.put("code", apiResultCode);
        apiDataSaveRst.put("message", apiResultMsg);
        return apiDataSaveRst;
    }

    @Override
    public String selectSchedulerInfoUseYnCheck(String schdulCd) throws Exception {
        return airplaneAPIMapper.selectSchedulerInfoUseYnCheck(schdulCd);
    }
}
