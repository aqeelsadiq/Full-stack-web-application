package com.peach.controller;

import com.peach.service.reportService;
import com.peach.service.postalcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class reportController {

    @Autowired
    private reportService reportService;

    @Autowired
    private postalcodeService postalcodeService;

    @GetMapping("/top-manufacturers")
    public List<Map<String, Object>> viewTopManufacturer() {
        return reportService.viewTopManufacturer();
    }

    @GetMapping("/manufacturer-drilldown/{manufacturerName}")
    public List<Map<String, Object>> manufacturerDrilldown(@PathVariable String manufacturerName) {
        return reportService.manufacturerDrilldown(manufacturerName);
    }

    @GetMapping("/search-manufacturer-model")
    public List<Map<String, Object>> searchManufacturerModel(@RequestParam String keyword) {
        return reportService.searchManufacturerModel(keyword);
    }

    @GetMapping("/view-heating-cooling/airconditioner")
    public List<Map<String, Object>> viewAirConditioner() {
        return reportService.viewAirConditioner();
    }

    @GetMapping("/view-heating-cooling/heater")
    public List<Map<String, Object>> viewHeater() {
        return reportService.viewHeater();
    }

    @GetMapping("/view-heating-cooling/heatpump")
    public List<Map<String, Object>> viewHeatPump() {
        return reportService.viewHeatPump();
    }

    @GetMapping("/view-waterheater-by-state")
    public List<Map<String, Object>> viewWaterHeaterByState() {
        return reportService.viewWaterHeaterByState();
    }

    @GetMapping("/view-state-drilldown/{state}")
    public List<Map<String, Object>> viewStateDrilldown(@PathVariable String state) {
        return reportService.viewStateDrilldown(state);
    }

    @GetMapping("/view-off-the-grid/state-with-most-offthegrid")
    public List<Map<String, Object>> stateWithMostOffTheGrid() {
        return reportService.stateWithMostOffTheGrid();
    }

    @GetMapping("/view-off-the-grid/average-storagekwh")
    public List<Map<String, Object>> averageStorageKwh() {
        return reportService.averageStorageKwh();
    }

    @GetMapping("/view-off-the-grid/percentage-by-generation-type")
    public List<Map<String, Object>> percentageByGenerationType() {
        return reportService.percentageByGenerationType();
    }

    @GetMapping("/view-off-the-grid/average-waterheater-capacity")
    public List<Map<String, Object>> averageWaterHeaterCapacity() {
        return reportService.averageWaterHeaterCapacity();
    }

    @GetMapping("/view-off-the-grid/btu-by-appliance-type")
    public List<Map<String, Object>> btuByApplianceType() {
        return reportService.btuByApplianceType();
    }

    @GetMapping("/household-by-radius")
    public List<Map<String, Object>> householdByRadius(@RequestParam String postalCode, @RequestParam Integer radius) {
        if(!postalcodeService.checkPostalCode(postalCode)) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "invalid postal code");
            List<Map<String, Object>> res = new ArrayList<>();
            res.add(error);
            return (res);
        }
        else {
            return reportService.householdByRadius(postalCode, radius);
        }
    }

}
