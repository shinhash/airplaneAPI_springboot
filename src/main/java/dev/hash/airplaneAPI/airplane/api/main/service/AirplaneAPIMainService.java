package dev.hash.airplaneAPI.airplane.api.main.service;

import java.util.List;
import java.util.Map;

public interface AirplaneAPIMainService {

    public List<Map<String, Object>> getAirplaneList() throws Exception;

    public Map<String, String> saveAirplaneList(Map<String, Object> apiResult) throws Exception;

    public String selectSchedulerInfoUseYnCheck(String schdulCd) throws Exception;
}
