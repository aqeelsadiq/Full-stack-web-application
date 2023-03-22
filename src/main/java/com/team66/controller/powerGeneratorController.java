package com.team66.controller;

import com.team66.entity.powerGenerator;
import com.team66.service.powerGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power-generator")
public class powerGeneratorController {

    @Autowired
    private powerGeneratorService powerGeneratorService;

    @PostMapping("/add")
    public void addPowerGenerator(@CookieValue(value = "cookieEmail") String email, @RequestBody Map<String, Object> queryMap) {
        Integer powerGeneratorNum = (Integer) queryMap.get("powerGeneratorNum");
        Integer monthlyKwh = (Integer) queryMap.get("monthlyKwh");
        Integer storageKwh = (Integer) queryMap.get("storageKwh");
        String generationType = (String) queryMap.get("generationType");
        powerGeneratorService.addPowerGenerator(new powerGenerator(email, powerGeneratorNum, monthlyKwh, storageKwh, generationType));
    }

    @GetMapping("/view")
    public List<Map<String, Object>> viewPowerGenerator(@CookieValue(value = "cookieEmail") String email) {
        return powerGeneratorService.viewPowerGenerator(email);
    }

    @DeleteMapping("/delete/{powerGeneratorNum}")
    public List<Map<String, Object>> deletePowerGenerator(@CookieValue(value = "cookieEmail") String email, @PathVariable Integer powerGeneratorNum) {
        powerGeneratorService.deletePowerGenerator(email, powerGeneratorNum);
        return powerGeneratorService.viewPowerGenerator(email);
    }
}
