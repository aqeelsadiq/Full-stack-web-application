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
    public void addPowerGenerator(@RequestBody Map<String, Object> queryMap) {
        String email = (String) queryMap.get("email");
        Integer powerGeneratorNum = (Integer) queryMap.get("powerGeneratorNum");
        Integer monthlyKwh = (Integer) queryMap.get("monthlyKwh");
        Integer storageKwh = (Integer) queryMap.get("storageKwh");
        String generationType = (String) queryMap.get("generationType");
        powerGeneratorService.addPowerGenerator(new powerGenerator(email, powerGeneratorNum, monthlyKwh, storageKwh, generationType));
    }

    @GetMapping("/view/{email}")
    public List<Map<String, Object>> viewPowerGenerator(@PathVariable String email) {
        return powerGeneratorService.viewPowerGenerator(email);
    }

    @DeleteMapping("/delete")
    public List<Map<String, Object>> deletePowerGenerator(@RequestBody Map<String, Object> queryMap) {
        String email = (String) queryMap.get("email");
        Integer powerGeneratorNum = (Integer) queryMap.get("powerGeneratorNum");
        powerGeneratorService.deletePowerGenerator(email, powerGeneratorNum);
        return powerGeneratorService.viewPowerGenerator(email);
    }
}
