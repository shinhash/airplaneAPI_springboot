package dev.hash.airplaneAPI.airplane.comm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AirplaneAPICommMapper {
	
	/**
	 * select validate value list
	 * @param validateCode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> validateList(Map<String, String> commCdInfo) throws Exception;
}
