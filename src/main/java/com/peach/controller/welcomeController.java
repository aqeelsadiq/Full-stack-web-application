package com.peach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class welcomeController {

    @RequestMapping("/")
    public String mainMenu() {
        return "main_menu";
    }

    @RequestMapping("/view-report-page")
    public String viewReportPage() { return "view_report";}

    @RequestMapping("/household-info-page")
    public String viewHouseholdInfoPage() { return "household_info";}

    @RequestMapping("/top-manufacturer-page")
    public String viewTopManufacturerPage() {return "top_manufacturer";}

    @RequestMapping("/search-manufacturer-model-page")
    public String viewSearchManufacturerModelPage() {return "search_manufacturer_model";}

    @RequestMapping("/view-heating-cooling-page")
    public String viewMethodPage() {return "view_heating_cooling";}

    @RequestMapping("/view-water-heater-page")
    public String viewWaterHeaterPager() {return "view_water_heater";}

    @RequestMapping("/view-off-grid-page")
    public String viewOffTheGridPage() {return "view_off_grid";}

    @RequestMapping("/view-householdavg-page")
    public String viewHouseholdAvgPage() {return "view_householdavg";}

    @RequestMapping("/view-manufacturer-drilldown-page")
    public String viewManufacturerDrilldownPage() {return "view_manufacturer_drilldown";}

    @RequestMapping("/water-heater-drilldown-page")
    public String waterHeaterDrilldownPage() {return "water_heater_drilldown";}
}
