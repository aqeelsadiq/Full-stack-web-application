package com.peach.controller;

import com.peach.entity.*;
import com.peach.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appliance")
public class applianceController {

    @Autowired
    private manufacturerService manuService;

    @Autowired
    private applianceService applianceService;

    @Autowired
    private waterHeaterService waterHeaterService;

    @Autowired
    private airHandlerService airHandlerService;

    @Autowired
    private airConditionerService airConditionerService;

    @Autowired
    private heaterService heaterService;

    @Autowired
    private heatPumpService heatPumpService;


    @GetMapping("/manu-list")
    public List<manufacturer> manufacturerList() {
        try {
            return manuService.manufacturerList();
        }
        catch(DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/add")
    public void addAppliance(@CookieValue(value = "cookieEmail") String email, @RequestBody Map<String, Object> queryMap) {
        Integer applianceNum = (Integer) queryMap.get("applianceNum");
        Integer btu = (Integer) queryMap.get("btu");
        String modelName = (String) queryMap.get("modelName");
        String manufacturerName = (String) queryMap.get("manufacturerName");
        applianceService.addAppliance(new appliance(email, applianceNum, btu, modelName, manufacturerName));

        String applianceType = (String) queryMap.get("applianceType");
        if(applianceType.equals("Water Heater")) {
            String energySource = (String) queryMap.get("energySource");
            Double capacity = (Double) queryMap.get("capacity");
            Integer currentTemperatureSetting = (Integer) queryMap.get("currentTemperatureSetting");
            waterHeaterService.addWaterHeater(new waterHeater(email, applianceNum, energySource, capacity, currentTemperatureSetting));
        }
        else if(applianceType.equals("Air Handler")) {
            airHandlerService.addAirHandler(new airHandler(email, applianceNum));
            String airConditioner = (String) queryMap.get("airConditioner");
            String heater = (String) queryMap.get("heater");
            String heatPump = (String) queryMap.get("heatPump");
            if(airConditioner != null) {
                Double eer = (Double) queryMap.get("eer");
                airConditionerService.addAirConditioner(new airConditioner(email, applianceNum, eer));
            }
            if(heater != null) {
                String energySource = (String) queryMap.get("energySource");
                heaterService.addHeater(new heater(email, applianceNum, energySource));
            }
            if(heatPump != null) {
                Double seer = (Double) queryMap.get("seer");
                Double hspf = (Double) queryMap.get("hspf");
                heatPumpService.addHeatPump(new heatPump(email, applianceNum, seer, hspf));
            }
        }
    }

    @GetMapping("/view")
    public List<Map<String, Object>> viewAppliance(@CookieValue(value = "cookieEmail") String email) {
        return applianceService.viewAppliance(email);
    }

    @DeleteMapping("/delete/{applianceNum}")
    public List<Map<String, Object>> deleteAppliance(@CookieValue(value = "cookieEmail") String email, @PathVariable Integer applianceNum) {
        applianceService.deleteAppliance(email, applianceNum);
        return applianceService.viewAppliance(email);
    }

    @GetMapping("/has-appliance-left")
    public boolean hasApplianceLeft(@CookieValue(value = "cookieEmail") String email) {
        return applianceService.hasApplianceLeft(email);
    }
}
