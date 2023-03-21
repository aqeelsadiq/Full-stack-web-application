package com.team66.service;

import com.team66.entity.publicUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class publicUtilitiesService {

    @Autowired
    private JdbcTemplate template;

    public void addPublicUtilities(publicUtilities newPU) {
        String sql = "INSERT INTO PublicUtilities VALUES (?,?)";
        template.update(sql, newPU.getEmail(), newPU.getType());
    }
}
