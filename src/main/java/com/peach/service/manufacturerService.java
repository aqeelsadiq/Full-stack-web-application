package com.peach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.peach.entity.manufacturer;

import java.util.List;

@Service
@Component
public class manufacturerService {

    @Autowired
    private JdbcTemplate template;

    public List<manufacturer> manufacturerList() {
        String sql = "SELECT manufacturer_name FROM Manufacturer";
        return template.query(sql, new BeanPropertyRowMapper(manufacturer.class));
    }
}
