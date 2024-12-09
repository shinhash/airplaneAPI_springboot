package dev.hash.airplaneAPI.airplane.scheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hash.airplaneAPI.airplane.api.service.AirplaneAPIService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings({"unchecked"})
@Component
public class AirplaneAPIScheduler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource(name="airplaneAPIService")
    private AirplaneAPIService airplaneAPIService;

    @Scheduled(cron="0 10 0 * * *")
    public void airPlaneScheduler() throws Exception {
        String schdulCd = "SJ002";

        String useYn = airplaneAPIService.selectSchedulerInfoUseYnCheck(schdulCd);
        LOGGER.info("useYn : {}", useYn);
        if(useYn.equals("Y")) {
            LOGGER.info("스케쥴러 테스트");
            Map<String, String> apiDataSaveRst = new HashMap<String, String>();
            try {
                InputStream inputStream = getClass().getResourceAsStream("/prop/api/apiValues.properties");
                String apiUrl = "";
                if(inputStream != null) {
                    Reader reader = new InputStreamReader(inputStream);
                    Properties properties = new Properties();
                    properties.load(reader);
                    apiUrl = properties.getProperty("api.airplain.url") + "?" + properties.getProperty("api.airplain.key") + "&" + properties.getProperty("api.airplain.returnType");
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

                apiDataSaveRst = airplaneAPIService.saveAirplaneList((Map<String, Object>)apiResult.get("response"));
            }catch(Exception e) {
                apiDataSaveRst.put("result", e.toString());
                apiDataSaveRst.put("code", "");
                apiDataSaveRst.put("message", "");
            }
            LOGGER.info("apiDataSaveRst : {}", apiDataSaveRst);
        }else {
            LOGGER.info("airPlainScheduler SCHEDULER IS NOT USED");
        }
    }
}
