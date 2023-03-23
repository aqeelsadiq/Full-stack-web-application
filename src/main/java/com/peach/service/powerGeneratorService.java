package com.peach.service;

import com.peach.entity.powerGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Component
public class powerGeneratorService {

    @Autowired
    private JdbcTemplate template;

    public boolean checkIfOffTheGrid(String email) {
        String sql = "SELECT Household.email FROM Household LEFT OUTER JOIN PublicUtilities ON Household.email = PublicUtilities.email WHERE type IS NULL AND Household.email = ?";
        return(template.queryForMap(sql, email).size()>0? true : false);
    }

    public void addPowerGenerator(powerGenerator newPowerGenerator) {
        if(newPowerGenerator.getStorage_kwh() != null) {
            String sql = "INSERT INTO PowerGenerator VALUES (?,?,?,?,?)";
            template.update(sql, newPowerGenerator.getEmail(), newPowerGenerator.getPower_generator_num(), newPowerGenerator.getMonthly_kwh(), newPowerGenerator.getStorage_kwh(), newPowerGenerator.getGeneration_type());
        }
        else {
            String sql = "INSERT INTO PowerGenerator VALUES (?,?,?,?)";
            template.update(sql, newPowerGenerator.getEmail(), newPowerGenerator.getPower_generator_num(), newPowerGenerator.getMonthly_kwh(), newPowerGenerator.getGeneration_type());
        }
    }

    public List<Map<String, Object>> viewPowerGenerator(String email) {
        String sql = "SELECT power_generator_num, generation_type, monthly_kwh, IFNULL(storage_kwh, '') AS battery_kwh FROM PowerGenerator WHERE email = ? ORDER BY power_generator_num;";
        return template.queryForList(sql, email);
    }

    public void deletePowerGenerator(String email, Integer powerGeneratorNum) {
        String sql = "DELETE FROM PowerGenerator WHERE email = ? AND power_generator_num = ?";
        template.update(sql, email, powerGeneratorNum);
    }
}
