package com.peach.service;

import com.peach.entity.household;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class householdService {

    @Autowired
    private JdbcTemplate template;

    //check if email already exist
    public Boolean checkEmail(String email) {
        String sql = "SELECT email FROM Household WHERE email = ?";
        return (template.queryForObject(sql, String.class, email) != null? true : false);
    }

    //insert new household
    public void addHousehold(household newHousehold) {
        String sql;
        if(newHousehold.getHeating() == null && newHousehold.getCooling() == null) {
            sql = "INSERT INTO Household(email, household_type, square_footage, postal_code) VALUES (?,?,?,?)";
            template.update(sql, newHousehold.getEmail(), newHousehold.getHousehold_type(), newHousehold.getSquare_footage(), newHousehold.getPostalCode());
        }
        else if(newHousehold.getHeating() == null) {
            sql = "INSERT INTO Household(email, household_type, cooling, square_footage, postal_code) VALUES (?,?,?,?,?)";
            template.update(sql, newHousehold.getEmail(), newHousehold.getHousehold_type(), newHousehold.getCooling(), newHousehold.getSquare_footage(), newHousehold.getPostalCode());
        }
        else if(newHousehold.getCooling() == null) {
            sql = "INSERT INTO Household(email, household_type, heating, square_footage, postal_code) VALUES (?,?,?,?,?)";
            template.update(sql, newHousehold.getEmail(), newHousehold.getHousehold_type(), newHousehold.getHeating(), newHousehold.getSquare_footage(), newHousehold.getPostalCode());
        }
        else {
            sql = "INSERT INTO Household VALUES (?,?,?,?,?,?)";
            template.update(sql, newHousehold.getEmail(), newHousehold.getHousehold_type(), newHousehold.getHeating(), newHousehold.getCooling(), newHousehold.getSquare_footage(), newHousehold.getPostalCode());
        }
    }

    //return all household
    public List<String> allHousehold(){
        String sql = "SELECT email FROM Household";
        return template.queryForList(sql, String.class);
    }
}
