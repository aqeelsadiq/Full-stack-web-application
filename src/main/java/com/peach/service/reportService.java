package com.peach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Component
public class reportService {

    @Autowired
    private JdbcTemplate template;

    public List<Map<String, Object>> viewTopManufacturer() {
        String sql = "SELECT Manufacturer.manufacturer_name, COUNT(appliance_num) AS count, 'Drilldown Report' AS drilldown_report FROM Manufacturer LEFT OUTER JOIN Appliance ON Appliance.manufacturer_name = Manufacturer.manufacturer_name GROUP BY manufacturer_name ORDER BY count DESC LIMIT 25";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> manufacturerDrilldown(String manufacturerName) {
        String sql = "(SELECT ? AS manufacturer_name, 'Air Handler' AS appliance_type, count(Appliance.appliance_num) AS count FROM Appliance JOIN AirHandler ON Appliance.email = Airhandler.email AND Appliance.appliance_num = Airhandler.appliance_num WHERE manufacturer_name = ?) UNION (SELECT ? AS manufacturer_name, 'Air Conditioner' AS appliance_type, count(Appliance.appliance_num) AS count FROM Appliance JOIN AirConditioner ON Appliance.email = AirConditioner.email AND Appliance.appliance_num = AirConditioner.appliance_num WHERE manufacturer_name = ?) UNION (SELECT ? AS manufacturer_name, 'Heater' AS appliance_type, count(Appliance.appliance_num) AS count FROM Appliance JOIN Heater ON Appliance.email = Heater.email AND Appliance.appliance_num = Heater.appliance_num WHERE manufacturer_name = ?) UNION (SELECT ? AS manufacturer_name, 'Heat Pump' AS appliance_type, count(Appliance.appliance_num) AS count FROM Appliance JOIN HeatPump ON Appliance.email = HeatPump.email AND Appliance.appliance_num = HeatPump.appliance_num WHERE manufacturer_name = ?) UNION (SELECT ? AS manufacturer_name, 'Water Heater' AS appliance_type, count(Appliance.appliance_num) AS count FROM Appliance JOIN WaterHeater ON Appliance.email = WaterHeater.email AND Appliance.appliance_num = WaterHeater.appliance_num WHERE manufacturer_name = ?)";
        return template.queryForList(sql, manufacturerName, manufacturerName, manufacturerName, manufacturerName, manufacturerName, manufacturerName, manufacturerName, manufacturerName, manufacturerName, manufacturerName);
    }

    public List<Map<String, Object>> searchManufacturerModel(String keyword) {
        String sql = "SELECT DISTINCT IFNULL(Manufacturer.manufacturer_name, '') AS manufacturer_name, IFNULL(model_name, '') AS model_name FROM Manufacturer LEFT OUTER JOIN Appliance ON Manufacturer.manufacturer_name = Appliance.manufacturer_name WHERE Manufacturer.manufacturer_name LIKE ? OR model_name LIKE ? ORDER BY manufacturer_name, model_name";
        keyword = "%" + keyword + "%";
        return template.queryForList(sql, keyword, keyword);
    }

    public List<Map<String, Object>> viewAirConditioner() {
        String sql = "SELECT household_type, COUNT(Household.email) AS count_airconditioner, ROUND(AVG(btu),0) AS average_ac_btu, ROUND(AVG(eer),1) AS average_ac_eer FROM Household JOIN Appliance ON Household.email = Appliance.email JOIN AirConditioner ON Appliance.email = AirConditioner.email AND Appliance.appliance_num = AirConditioner.appliance_num GROUP BY household_type";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> viewHeater() {
        String sql = "SELECT household_type, COUNT(H1.email) AS count_heater, ROUND(AVG(btu),0) AS average_heater_btu, (SELECT energy_source FROM Heater AS HT2 JOIN Household AS H2 ON HT2.email = H2.email WHERE H2.household_type = H1.household_type GROUP BY energy_source ORDER BY COUNT(energy_source) DESC LIMIT 1) AS most_common_energy_source FROM Household AS H1 JOIN Appliance ON H1.email = Appliance.email JOIN Heater AS HT1 ON Appliance.email = HT1.email AND Appliance.appliance_num = HT1.appliance_num GROUP BY H1.household_type";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> viewHeatPump() {
        String sql = "SELECT household_type, COUNT(Household.email) AS count_heatpump, ROUND(AVG(btu),0) AS average_hp_btu, ROUND(AVG(seer),1) AS average_hp_seer, ROUND(AVG(hspf),1) AS average_hp_hspf FROM Household JOIN Appliance ON Household.email = Appliance.email JOIN HeatPump ON Appliance.email = HeatPump.email AND Appliance.appliance_num = HeatPump.appliance_num GROUP BY household_type;";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> viewWaterHeaterByState() {
        String sql = "SELECT PostalCode.state, IFNULL(ROUND(AVG(Capacity),1), 0) AS avg_capacity, IFNULL(ROUND(AVG(BTU),1), 0)AS avg_BTU, IFNULL(ROUND(AVG(current_temperature_setting),1), 0) AS avg_currentTemperatureSetting, IFNULL(COUNT(CASE WHEN current_temperature_setting IS NOT NULL THEN Household.Email END), 0) AS count_with_setting, IFNULL(COUNT(CASE WHEN current_temperature_setting IS NULL THEN Household.Email END), 0) AS count_without_setting, 'Drilldown report' as drilldown_report FROM Household JOIN (SELECT WaterHeater.email, WaterHeater.appliance_num, Capacity, BTU, current_temperature_setting from WaterHeater JOIN Appliance ON WaterHeater.email = Appliance.email AND WaterHeater.appliance_num = Appliance.appliance_num) as appliance_waterheater ON Household.email = appliance_waterheater.email JOIN PostalCode ON Household.postal_code = PostalCode.postal_code GROUP BY State ORDER BY State";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> viewStateDrilldown(String state) {
        String sql = "SELECT ? AS state, energy_source, ROUND(MIN(Capacity),0) AS min_capacity, ROUND(AVG(Capacity),0) AS avg_capacity, ROUND(MAX(Capacity),0) AS max_capacity, ROUND(MIN(current_temperature_setting),0) AS min_temperature, ROUND(AVG(current_temperature_setting),0) AS avg_temperature, ROUND(MAX(current_temperature_setting),0) AS max_temperature FROM Household JOIN PostalCode ON Household.postal_code = PostalCode.postal_code JOIN WaterHeater ON WaterHeater.email = Household.email WHERE state = ? GROUP BY energy_source ORDER BY energy_source";
        return template.queryForList(sql, state, state);
    }

    public List<Map<String, Object>> stateWithMostOffTheGrid() {
        String sql = "SELECT state, COUNT(Household.email) AS count_off_the_grid FROM Household JOIN PostalCode ON Household.postal_code = PostalCode.postal_code LEFT OUTER JOIN PublicUtilities ON Household.email = PublicUtilities.email WHERE type IS NULL GROUP BY state ORDER BY count_off_the_grid DESC LIMIT 1";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> averageStorageKwh() {
        String sql = "SELECT IFNULL(ROUND(AVG(storage_kwh),0),0) AS average_storage_capacity FROM PowerGenerator LEFT OUTER JOIN PublicUtilities ON PowerGenerator.email = PublicUtilities.email WHERE type is NULL";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> percentageByGenerationType() {
        String sql = "SELECT ROUND(count_solar*100/(count_mix + count_solar + count_wind),1) AS 'solar_electric_percentage', ROUND(count_wind*100/(count_mix + count_solar + count_wind),1) AS 'wind_percentage', ROUND(count_mix*100/(count_mix + count_solar + count_wind),1) AS 'mix_percentage' FROM (SELECT COUNT(DISTINCT email) AS count_mix FROM (SELECT email, COUNT(DISTINCT generation_type) AS c1 FROM (SELECT PowerGenerator.email, generation_type FROM PowerGenerator LEFT OUTER JOIN PublicUtilities ON PowerGenerator.email = PublicUtilities.email WHERE PublicUtilities.type IS NULL) AS t1 GROUP BY email HAVING c1 > 1) AS t2) AS mix_table, (SELECT COUNT(DISTINCT t3.email) AS count_solar FROM (SELECT PowerGenerator.email, generation_type FROM PowerGenerator LEFT OUTER JOIN PublicUtilities ON PowerGenerator.email = PublicUtilities.email WHERE PublicUtilities.type IS NULL) AS t3 JOIN (SELECT email, COUNT(DISTINCT generation_type) AS c2 FROM PowerGenerator GROUP BY email HAVING c2 = 1) AS t4 ON t3.email = t4.email WHERE t3.generation_type = 'solar-electric') AS solar_table, (SELECT COUNT(DISTINCT t5.email) AS count_wind FROM (SELECT PowerGenerator.email, generation_type FROM PowerGenerator LEFT OUTER JOIN PublicUtilities ON PowerGenerator.email = PublicUtilities.email WHERE PublicUtilities.type IS NULL) AS t5 JOIN (SELECT email, COUNT(DISTINCT generation_type) AS c3 FROM PowerGenerator GROUP BY email HAVING c3 = 1) AS t6 ON t5.email = t6.email WHERE t5.generation_type = 'wind') AS wind_table";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> averageWaterHeaterCapacity() {
        String sql = "(SELECT 'Off the grid household' AS household_type, ROUND(AVG(capacity),1) AS avg_waterheater_capacity FROM WaterHeater LEFT OUTER JOIN PublicUtilities ON WaterHeater.email = PublicUtilities.email WHERE type IS NULL) UNION (SELECT 'On the grid household' AS household_type, ROUND(AVG(capacity),1) AS avg_waterheater_capacity FROM WaterHeater JOIN PublicUtilities ON waterHeater.email = PublicUtilities.email)";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> btuByApplianceType() {
        String sql = "(SELECT 'Air Handler' as appliance_type, MIN(btu) AS off_the_grid_min_btu, MAX(btu) AS off_the_grid_max_btu, ROUND(AVG(btu),0) AS off_the_grid_avg_btu FROM Appliance JOIN AirHandler ON Appliance.email = AirHandler.email AND Appliance.appliance_num = AirHandler.appliance_num LEFT OUTER JOIN PublicUtilities ON AirHandler.email = PublicUtilities.email WHERE type IS NULL) UNION (SELECT 'Air Conditioner' as appliance_type, MIN(btu) AS off_the_grid_min_btu, MAX(btu) AS off_the_grid_max_btu, ROUND(AVG(btu),0) AS off_the_grid_avg_btu FROM Appliance JOIN AirConditioner ON Appliance.email = AirConditioner.email AND Appliance.appliance_num = AirConditioner.appliance_num LEFT OUTER JOIN PublicUtilities ON AirConditioner.email = PublicUtilities.email WHERE type IS NULL) UNION (SELECT 'Heater' as appliance_type, MIN(btu) AS off_the_grid_min_btu, MAX(btu) AS off_the_grid_max_btu, ROUND(AVG(btu),0) AS off_the_grid_avg_btu FROM Appliance JOIN Heater ON Appliance.email = Heater.email AND Appliance.appliance_num = Heater.appliance_num LEFT OUTER JOIN PublicUtilities ON Heater.email = PublicUtilities.email WHERE type IS NULL) UNION (SELECT 'Heat Pump' as appliance_type, MIN(btu) AS off_the_grid_min_btu, MAX(btu) AS off_the_grid_max_btu, ROUND(AVG(btu),0) AS off_the_grid_avg_btu FROM Appliance JOIN HeatPump ON Appliance.email = HeatPump.email AND Appliance.appliance_num = HeatPump.appliance_num LEFT OUTER JOIN PublicUtilities ON HeatPump.email = PublicUtilities.email WHERE type IS NULL) UNION (SELECT 'Water Heater' as 'Appliance Type', MIN(btu) AS off_the_grid_min_btu, MAX(btu) AS off_the_grid_btu, ROUND(AVG(btu),0) AS off_the_grid_avg_btu FROM Appliance JOIN WaterHeater ON Appliance.email = WaterHeater.email AND Appliance.appliance_num = WaterHeater.appliance_num LEFT OUTER JOIN PublicUtilities ON WaterHeater.email = PublicUtilities.email WHERE type IS NULL)";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> householdByRadius(String postalCode, Integer radius) {
        String sql = "WITH currentPostal AS (SELECT radians(latitude) AS lat, radians(longitude) AS lon FROM PostalCode WHERE PostalCode.postal_code = ?), HouseHoldsInRadius AS (SELECT email, household_type, heating, cooling, square_footage, postal_code FROM Household NATURAL JOIN PostalCode CROSS JOIN currentPostal c WHERE 3958.75*2*atan2(power(power(sin((radians(latitude) - c.lat)/2),2) + cos(c.lat)*cos(radians(latitude))*power(sin((radians(longitude) - c.lon)/2),2),0.5),power(1-(power(sin((radians(latitude) - c.lat)/2),2) + cos(c.lat)*cos(radians(latitude))*power(sin((radians(longitude) - c.lon)/2),2)),0.5)) <= ?), StatOfHousehold AS (SELECT COUNT(*) AS count_of_household_in_radius, ROUND(AVG(square_footage),1) AS avg_square_footage, ROUND(AVG(heating),1) AS avg_heating, ROUND(AVG(cooling),1) AS avg_cooling FROM HouseHoldsInRadius), CountOfType AS (SELECT (SELECT COUNT(email) FROM houseHoldsInRadius WHERE household_type = 'house') AS count_of_house_type, (SELECT COUNT(email) FROM houseHoldsInRadius WHERE household_type = 'apartment') AS count_of_apartment_type, (SELECT COUNT(email) FROM houseHoldsInRadius WHERE household_type = 'townhome') AS count_of_townhome_type, (SELECT COUNT(email) FROM houseHoldsInRadius WHERE household_type = 'condominium') AS count_of_condominium_type, (SELECT COUNT(email) FROM houseHoldsInRadius WHERE household_type = 'mobile home') AS count_of_mobile_home_type), PublicUtilityUsed AS (SELECT IFNULL(group_concat(type), '') AS public_utility_used FROM (SELECT DISTINCT type FROM PublicUtilities Natural JOIN HouseHoldsInRadius) AS t), CountOfOffTheGrid AS (SELECT count(h.email) AS count FROM houseHoldsInRadius h LEFT OUTER JOIN PublicUtilities p ON p.email = h.email WHERE type IS NULL), HouseHoldsWithPower AS (SELECT * FROM HouseHoldsInRadius NATURAL JOIN PowerGenerator), StatOfHouseholdWithPower AS (SELECT COUNT(email) AS count, ROUND(AVG(total_monthly_kwh),1) AS avg_monthly_kwh, COUNT(total_storage_kwh) AS count_of_household_with_battery FROM (SELECT email, SUM(monthly_kwh) AS total_monthly_kwh, SUM(storage_kwh) AS total_storage_kwh FROM HouseHoldsWithPower GROUP BY email) AS s), MostCommonMethod AS(SELECT count(generation_type) count, generation_type FROM HouseHoldsWithPower GROUP BY generation_type ORDER BY count DESC LIMIT 1) SELECT ? postal_code, ? radius, StatOfHousehold.count_of_household_in_radius, CountOfType.count_of_house_type, CountOfType.count_of_apartment_type, CountOfType.count_of_townhome_type, CountOfType.count_of_condominium_type, CountOfType.count_of_mobile_home_type, StatOfHousehold.avg_square_footage, StatOfHousehold.avg_heating, StatOfHousehold.avg_cooling, PublicUtilityUsed.public_utility_used, CountOfOffTheGrid.count AS count_of_0ff_the_grid, StatOfHouseholdWithPower.count AS count_of_household_with_power, MostCommonMethod.generation_type AS most_common_generation_method, StatOfHouseholdWithPower.avg_monthly_kwh, StatOfHouseholdWithPower.count_of_household_with_battery FROM statOfHousehold CROSS JOIN CountOfType CROSS JOIN PublicUtilityUsed CROSS JOIN CountOfOffTheGrid CROSS JOIN StatOfHouseholdWithPower CROSS JOIN MostCommonMethod";
        return template.queryForList(sql, postalCode, radius, postalCode, radius);
    }
}