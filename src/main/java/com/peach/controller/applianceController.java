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

    //Generate appliance number
    private static int current_app_num = 1;

    public static void setCurrent_app_num(int curr_app_num) {
        applianceController.current_app_num = curr_app_num;
    }

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

    @PostMapping("/add-airhandler")
    //@CrossOrigin(origins = "http://localhost:63342/",allowCredentials = "true")
    //public void addAppliance(@CookieValue(value = "cookieEmail") String email, @RequestBody Map<String, Object> queryMap) {
    public void addAirhandler( @RequestBody Map<String, Object> queryMap) { //get input data from queryMap
        String email = (String) queryMap.get("email");
        Integer applianceNum = current_app_num;
        Integer btu = (Integer) queryMap.get("btu");
        String modelName = (String) queryMap.get("modelName");
        String manufacturerName = (String) queryMap.get("manufacturerName");
        applianceService.addAppliance(new appliance(email, applianceNum, btu, modelName, manufacturerName));
        current_app_num++;

        airHandlerService.addAirHandler(new airHandler(email, applianceNum));
        String airConditioner = (String) queryMap.get("airConditioner");
        String heater = (String) queryMap.get("heater");
        String heatPump = (String) queryMap.get("heatPump");
        if(airConditioner != null) {
            Double eer = ((Number) queryMap.get("eer")).doubleValue();
            airConditionerService.addAirConditioner(new airConditioner(email, applianceNum, eer));
        }
        if(heater != null) {
            String energySource = (String) queryMap.get("energySource");
            heaterService.addHeater(new heater(email, applianceNum, energySource));
        }
        if(heatPump != null) {
            Double seer = ((Number) queryMap.get("seer")).doubleValue();
            Double hspf = ((Number) queryMap.get("hspf")).doubleValue();
            heatPumpService.addHeatPump(new heatPump(email, applianceNum, seer, hspf));
        }
    }

    @PostMapping("/add-waterheater")
    public void addWaterHeater( @RequestBody Map<String, Object> queryMap) {
        String email = (String) queryMap.get("email");
        Integer applianceNum = current_app_num;
        Integer btu = (Integer) queryMap.get("btu");
        String modelName = (String) queryMap.get("modelName");
        String manufacturerName = (String) queryMap.get("manufacturerName");
        applianceService.addAppliance(new appliance(email, applianceNum, btu, modelName, manufacturerName));
        current_app_num++;

        String energySource = (String) queryMap.get("energySource");
        Double capacity = ((Number) queryMap.get("capacity")).doubleValue();
        Integer currentTemperatureSetting = (Integer) queryMap.get("currentTemperatureSetting");
        waterHeaterService.addWaterHeater(new waterHeater(email, applianceNum, energySource, capacity, currentTemperatureSetting));
    }

    @GetMapping("/view")
    public List<Map<String, Object>> viewAppliance(@RequestParam("email") String email) {
        return applianceService.viewAppliance(email);
    }

    @PostMapping("/delete")
    public List<Map<String, Object>> deleteAppliance(@RequestBody Map<String, Object> queryMap) {
        String email = (String) queryMap.get("email");
        Integer applianceNum = (Integer) queryMap.get("applianceNum");
        applianceService.deleteAppliance(email, applianceNum);
        return applianceService.viewAppliance(email);
    }

    @GetMapping("/has-appliance-left")
    public boolean hasApplianceLeft(@RequestParam("email") String email) {
        return applianceService.hasApplianceLeft(email);
    }
}
