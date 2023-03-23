package com.peach.service;

import com.peach.entity.appliance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Component
public class applianceService {

    @Autowired
    private JdbcTemplate template;

    public void addAppliance(appliance newAppliance) {
        if(newAppliance.getModel_name() != null) {
            String sql = "INSERT INTO appliance VALUES (?,?,?,?,?)";
            template.update(sql, newAppliance.getEmail(), newAppliance.getAppliance_num(), newAppliance.getBtu(), newAppliance.getModel_name(), newAppliance.getManufacturer_name());
        }
        else {
            String sql = "INSERT INTO Appliance VALUES (?,?,?,?)";
            template.update(sql, newAppliance.getEmail(), newAppliance.getAppliance_num(), newAppliance.getBtu(), newAppliance.getManufacturer_name());
        }
    }

    public List<Map<String, Object>> viewAppliance(String email) {
        String sql = "(SELECT WaterHeater.appliance_num, 'Water Heater' AS type, manufacturer_name, model_name FROM Appliance JOIN WaterHeater ON Appliance.email = WaterHeater.email AND Appliance.appliance_num = WaterHeater.appliance_num WHERE Appliance.email = ?) UNION (SELECT AirHandler.appliance_num, 'Air handler' AS Type, manufacturer_name, model_name FROM Appliance JOIN AirHandler ON Appliance.email = AirHandler.email AND Appliance.appliance_num = AirHandler.appliance_num WHERE Appliance.email = ?) ORDER BY appliance_num ASC";
        return template.queryForList(sql, email, email);
    }

    public void deleteAppliance(String email, Integer applianceNum) {
        String sql = "DELETE FROM Appliance where email = ? and appliance_num = ?";
        template.update(sql, email, applianceNum);
    }

    public boolean hasApplianceLeft(String email) {
        String sql = "SELECT COUNT(appliance_num) FROM Household JOIN Appliance ON Household.email = Appliance.email WHERE Household.email = ?";
        return template.queryForMap(sql, email).size() > 0? true : false;
    }
}
