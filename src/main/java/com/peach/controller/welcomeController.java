package com.peach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class welcomeController {

    @RequestMapping("/")
    public String mainMenu() {
        return "main_menu";
    }

    @RequestMapping("/household-info-page")
    public String viewHouseholdInfoPage() { return "add_household_info";}

    @RequestMapping("/choose-appliance-type-page")
    public String addAppliancePage() {return "appliance_choose_type";}

    @RequestMapping("/add-airhandler-page")
    public String addAirhandlerPage() {return "add_air_handler";}

    @RequestMapping("/add-waterheater-page")
    public String addWaterHeaterPage() {return "add_water_heater";}

    @RequestMapping("/view-appliance-page")
    public String viewAppliancePage() {return "view_appliance";}

    @RequestMapping("/add-power-generator-page")
    public String addPowerGenerationPage() {return "add_power_generator";}

    @RequestMapping("/view-power-generator-page")
    public String viewPowerGeneratorPage() {return "view_power_generator";}

    @RequestMapping("/view-report-page")
    public String viewReportPage() { return "view_report";}

    @RequestMapping("/top-manufacturer-page")
    public String viewTopManufacturerPage() {return "top_manufacturer";}

    @RequestMapping("/search-manufacturer-model-page")
    public String viewSearchManufacturerModelPage() {return "search_manufacturer_model";}

    @RequestMapping("/view-heating-cooling-method-page")
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

    @RequestMapping("/wrap-up-page")
    public String wrapUpPage() {return "wrap_up";}
}
