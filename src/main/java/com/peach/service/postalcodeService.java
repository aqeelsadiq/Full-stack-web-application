package com.peach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class postalcodeService {

    @Autowired
    private JdbcTemplate template;

    public boolean checkPostalCode(String postalCode) {
        String sql = "SELECT postal_code FROM PostalCode WHERE postal_code = ?";
        return (template.queryForObject(sql, String.class, postalCode)!= null? true:false);
    }
}
