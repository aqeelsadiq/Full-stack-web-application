package com.peach.controller;

import com.peach.entity.powerGenerator;
import com.peach.service.powerGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power-generator")
public class powerGeneratorController {

    @Autowired
    private powerGeneratorService powerGeneratorService;

    //Generate power generator number
    private static int current_pg_num = 1;

    public static void setCurrent_pg_num(int curr_pg_num) {
        powerGeneratorController.current_pg_num = curr_pg_num;
    }

    @GetMapping("/check-if-offthegrid")
    public boolean checkIfOffTheGrid(@CookieValue(value = "cookieEmail") String email) {
        return powerGeneratorService.checkIfOffTheGrid(email);
    }

    @PostMapping("/add")
    public String addPowerGenerator(@RequestBody Map<String, Object> queryMap) {
        String email = (String) queryMap.get("email");
        Integer powerGeneratorNum = current_pg_num;
        Integer monthlyKwh = (Integer) queryMap.get("monthlyKwh");
        Integer storageKwh = (Integer) queryMap.get("storageKwh");
        String generationType = (String) queryMap.get("generationType");
        powerGeneratorService.addPowerGenerator(new powerGenerator(email, powerGeneratorNum, monthlyKwh, storageKwh, generationType));
        current_pg_num++;
        return "success";
    }

    @GetMapping("/view")
    public List<Map<String, Object>> viewPowerGenerator(@RequestParam("email") String email) {
        return powerGeneratorService.viewPowerGenerator(email);
    }

    @PostMapping("/delete")
    public String deletePowerGenerator(@RequestBody Map<String, Object> queryMap) {
        String email = (String) queryMap.get("email");
        Integer powerGeneratorNum = (Integer) queryMap.get("powerGeneratorNum");
        /* this check will be done when next button is clicked, not when off-the-grid hh deletes the last appliance
        if(powerGeneratorService.checkIfOffTheGrid(email) && powerGeneratorService.viewPowerGenerator(email).size()==1) {
            return "not allowed";
        } else {
         */
        powerGeneratorService.deletePowerGenerator(email, powerGeneratorNum);
        return "success";
    }

    @PostMapping("/can-finish-pg")
    public String canFinishPG(@RequestBody Map<String, Object> queryMap) {
        String email = (String) queryMap.get("email");
        if(powerGeneratorService.checkIfOffTheGrid(email) && powerGeneratorService.viewPowerGenerator(email).size()==0) {
            return "not allowed";
        } else return "allowed";
    }
}
