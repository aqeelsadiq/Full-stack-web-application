package com.peach.service;

import com.peach.entity.airConditioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class airConditionerService {

    @Autowired
    private JdbcTemplate template;

    public void addAirConditioner(airConditioner newAirConditioner) {
        String sql = "INSERT INTO AirConditioner VALUES (?,?,?)";
        template.update(sql, newAirConditioner.getEmail(), newAirConditioner.getAppliance_num(), newAirConditioner.getEer());
    }
}
