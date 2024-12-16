package dev.hash.airplaneAPI.airplane.scheduler;

import dev.hash.airplaneAPI.airplane.api.main.service.AirplaneAPIMainService;
import dev.hash.airplaneAPI.airplane.comm.utils.AirplaneAPIUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AirplaneAPIScheduler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource(name="airplaneAPIMainService")
    private AirplaneAPIMainService airplaneAPIMainService;

    @Scheduled(cron="0 10 0 * * *")
    public void airPlaneScheduler() throws Exception {
        String schdulCd = "SJ002";

        String useYn = airplaneAPIMainService.selectSchedulerInfoUseYnCheck(schdulCd);
        LOGGER.info("useYn : {}", useYn);
        if(useYn.equals("Y")) {
            LOGGER.info("스케쥴러 테스트");
            Map<String, String> apiDataSaveRst = new HashMap<String, String>();
            try {
                Map<String, Object> responseMap = AirplaneAPIUtils.getAirplaneAPIResponse();
                apiDataSaveRst = airplaneAPIMainService.saveAirplaneList(responseMap);
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
