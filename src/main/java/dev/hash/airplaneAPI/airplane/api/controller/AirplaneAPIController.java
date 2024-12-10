package dev.hash.airplaneAPI.airplane.api.controller;

import dev.hash.airplaneAPI.airplane.api.service.AirplaneAPIService;
import dev.hash.airplaneAPI.airplane.utils.AirplaneAPIUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
public class AirplaneAPIController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource(name="airplaneAPIService")
    private AirplaneAPIService airplaneAPIService;

    @GetMapping(value="/index.do")
    public ModelAndView index(ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("api/index");
        return modelAndView;
    }

    @PostMapping(value="/saveAirplaneList.do")
    public Map<String, String> saveAirplaneList() throws Exception {
        Map<String, Object> responseMap = new AirplaneAPIUtils().getAirplaneAPIResponse();
        Map<String, String> apiDataSaveRst = airplaneAPIService.saveAirplaneList(responseMap);
        LOGGER.info("apiDataSaveRst : {}", apiDataSaveRst);
        return apiDataSaveRst;
    }

    @PostMapping(value="/getAirplaneList.do")
    public List<Map<String, Object>> getAirplaneList() throws Exception {
        return airplaneAPIService.getAirplaneList();
    }
}
