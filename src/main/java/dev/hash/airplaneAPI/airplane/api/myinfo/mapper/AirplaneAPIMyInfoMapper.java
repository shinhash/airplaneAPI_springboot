package dev.hash.airplaneAPI.airplane.api.myinfo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AirplaneAPIMyInfoMapper {

	public Map<String, Object> getMyInfo(String accessUserId) throws Exception;
	
	public int updateMyInfo(Map<String, Object> receiveJson) throws Exception;

}
