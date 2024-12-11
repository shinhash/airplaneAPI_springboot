package dev.hash.airplaneAPI.airplane.scheduler;

import dev.hash.airplaneAPI.airplane.api.service.AirplaneAPIService;
import dev.hash.airplaneAPI.airplane.utils.AirplaneAPIUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
                Map<String, Object> responseMap = new AirplaneAPIUtils().getAirplaneAPIResponse();
                apiDataSaveRst = airplaneAPIService.saveAirplaneList(responseMap);
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
