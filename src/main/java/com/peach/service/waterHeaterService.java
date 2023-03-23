package com.peach.service;

import com.peach.entity.waterHeater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class waterHeaterService {

    @Autowired
    private JdbcTemplate template;

    public void addWaterHeater(waterHeater newWaterHeater) {
        if(newWaterHeater.getCurrent_temperature_setting() != null) {
            String sql = "INSERT INTO WaterHeater VALUES (?,?,?,?,?)";
            template.update(sql, newWaterHeater.getEmail(), newWaterHeater.getAppliance_num(), newWaterHeater.getEnergy_source(), newWaterHeater.getCapacity(), newWaterHeater.getCurrent_temperature_setting());
        }
        else {
            String sql = "INSERT INTO WaterHeater VALUES (?,?,?,?)";
            template.update(sql, newWaterHeater.getEmail(), newWaterHeater.getAppliance_num(), newWaterHeater.getEnergy_source(), newWaterHeater.getCapacity());
        }
    }

}

