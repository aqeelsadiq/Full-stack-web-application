package com.peach.service;

import com.peach.entity.airHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class airHandlerService {

    @Autowired
    private JdbcTemplate template;

    public void addAirHandler(airHandler newAirHandler) {
        String sql = "INSERT INTO AirHandler VALUES (?,?)";
        template.update(sql, newAirHandler.getEmail(), newAirHandler.getAppliance_num());
    }
}
