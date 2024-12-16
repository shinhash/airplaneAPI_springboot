package dev.hash.airplaneAPI.airplane.api.main.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AirplaneAPIMainMapper {

    public List<Map<String, Object>> getAirplaneList() throws Exception;

    public int checkAirplaneInfo(Map<String, Object> apiResultItemInfo) throws Exception;

    public void saveAirplaneInfo(Map<String, Object> apiResultItemInfo) throws Exception;

    public String selectSchedulerInfoUseYnCheck(String schdulCd) throws Exception;
}
