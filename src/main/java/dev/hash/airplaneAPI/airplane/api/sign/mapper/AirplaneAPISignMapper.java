package dev.hash.airplaneAPI.airplane.api.sign.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AirplaneAPISignMapper {
	
	public Map<String, Object> getUserInfo(String userId) throws Exception;

	public int getIdCheckCnt(String userId) throws Exception;

	public int insertUserInfo(Map<String, Object> receiveJson) throws Exception;

}
