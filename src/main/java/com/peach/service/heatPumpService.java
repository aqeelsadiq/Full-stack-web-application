package com.peach.service;

import com.peach.entity.heatPump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class heatPumpService {

    @Autowired
    private JdbcTemplate template;

    public void addHeatPump(heatPump newHeatPump) {
        String sql = "INSERT INTO HeatPump VALUES (?,?,?,?)";
        template.update(sql, newHeatPump.getEmail(), newHeatPump.getAppliance_num(), newHeatPump.getSeer(), newHeatPump.getHspf());
    }
}
