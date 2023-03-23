package com.peach.service;

import com.peach.entity.heater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class heaterService {

    @Autowired
    private JdbcTemplate template;

    public void addHeater(heater newHeater) {
        String sql = "INSERT INTO Heater VALUES (?,?,?)";
        template.update(sql, newHeater.getEmail(), newHeater.getAppliance_num(), newHeater.getEnergy_source());
    }
}
