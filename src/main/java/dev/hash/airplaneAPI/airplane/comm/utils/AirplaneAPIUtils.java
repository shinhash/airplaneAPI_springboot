package dev.hash.airplaneAPI.airplane.comm.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AirplaneAPIUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AirplaneAPIUtils.class);
	
	public static Map<String, Object> getAirplaneAPIResponse() throws Exception {
		InputStream inputStream = AirplaneAPIUtils.class.getResourceAsStream("/prop/api/apiValues.properties");
        StringBuffer apiUrl = new StringBuffer();
        if(inputStream != null) {
            Reader reader = new InputStreamReader(inputStream);
            Properties properties = new Properties();
            properties.load(reader);
            
            apiUrl.append(properties.getProperty("api.airplane.url"));
            apiUrl.append("?");
            apiUrl.append(properties.getProperty("api.airplane.key"));
            apiUrl.append("&");
            apiUrl.append(properties.getProperty("api.airplane.returnType"));
            apiUrl.append("&from_time=0900&to_time=1000");
        }
        LOGGER.info("apiUrl : {}", apiUrl);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> apiResp = restTemplate.getForEntity(apiUrl.toString(), String.class);
        LOGGER.info("JSON result : {}", apiResp.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>(){};
        Map<String, Object> objMapRst = objectMapper.readValue(apiResp.getBody(), typeReference);
        LOGGER.info("ObjectMapper result : {}", objMapRst);
        
        Map<String, Object> responseMap = (Map<String, Object>) objMapRst.get("response");
        LOGGER.info("responseMap result : {}", responseMap);
        
        return responseMap;
	}

}
