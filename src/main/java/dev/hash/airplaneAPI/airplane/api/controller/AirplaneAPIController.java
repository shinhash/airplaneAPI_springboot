package dev.hash.airplaneAPI.airplane.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hash.airplaneAPI.airplane.api.service.AirplaneAPIService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings({"unchecked"})
@Slf4j
@RestController
public class AirplaneAPIController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource(name="airplaneAPIService")
    private AirplaneAPIService airplaneAPIService;

    @RequestMapping(value="/index.do")
    public ModelAndView index(ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("api/index");
        return modelAndView;
    }

    @RequestMapping(value="/saveAirplaneList.do", method={RequestMethod.POST})
    public Map<String, String> saveAirplaneList() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/prop/api/apiValues.properties");
        String apiUrl = "";
        if(inputStream != null) {
            Reader reader = new InputStreamReader(inputStream);
            Properties properties = new Properties();
            properties.load(reader);
            apiUrl = properties.getProperty("api.airplane.url") + "?" + properties.getProperty("api.airplane.key") + "&" + properties.getProperty("api.airplane.returnType");
            apiUrl += "&from_time=0900&to_time=1000";
        }
        LOGGER.info("apiUrl : {}", apiUrl);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> apiResp = restTemplate.getForEntity(apiUrl, String.class);
        LOGGER.info("JSONObject result : {}", apiResp.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>(){};
        Map<String, Object> apiResult = objectMapper.readValue(apiResp.getBody(), typeReference);
        LOGGER.info("ObjectMapper result : {}", apiResult);

        Map<String, String> apiDataSaveRst = airplaneAPIService.saveAirplaneList((Map<String, Object>)apiResult.get("response"));
        LOGGER.info("apiDataSaveRst : {}", apiDataSaveRst);
        return apiDataSaveRst;
    }

    @RequestMapping(value="/getAirplaneList.do")
    public List<Map<String, Object>> getAirplaneList() throws Exception {
        return airplaneAPIService.getAirplaneList();
    }
}
