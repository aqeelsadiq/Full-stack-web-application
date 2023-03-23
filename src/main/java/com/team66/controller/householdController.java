package com.team66.controller;

import com.team66.entity.publicUtilities;
import com.team66.service.publicUtilitiesService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team66.service.householdService;
import com.team66.service.postalcodeService;
import com.team66.entity.household;

import java.util.Map;


@RestController
@RequestMapping("/household-info")
public class householdController {

    @Autowired
    private householdService householdService;
    @Autowired
    private postalcodeService postalcodeService;
    @Autowired
    private publicUtilitiesService puService;

    @PostMapping("/add-household")
    public String addHousehold(@RequestBody Map<String, Object> queryMap, HttpServletResponse response) {
        String email = (String) queryMap.get("email");
        String householdType = (String) queryMap.get("householdType");
        Integer heating = (Integer) queryMap.get("heating");
        Integer cooling = (Integer) queryMap.get("cooling");
        Integer sqft = (Integer) queryMap.get("squareFootage");
        String postalCode = (String) queryMap.get("postalCode");
        String electric = (String) queryMap.get("electric");
        String gas = (String) queryMap.get("gas");
        String steam = (String) queryMap.get("steam");
        String fuelOil = (String) queryMap.get("fuelOil");
        if(householdService.checkEmail(email)) return("Email already exists");
        else if(!postalcodeService.checkPostalCode(postalCode)) return("Invalid postal code");
        else {
            household newHousehold = new household(email, householdType, heating, cooling, sqft, postalCode);
            householdService.addHousehold(newHousehold);
            if(electric != null) {
                publicUtilities newPu = new publicUtilities(email, "electric");
                puService.addPublicUtilities(newPu);
            }
            if(gas != null) {
                publicUtilities newPu = new publicUtilities(email, "gas");
                puService.addPublicUtilities(newPu);
            }
            if(steam != null) {
                publicUtilities newPu = new publicUtilities(email, "steam");
                puService.addPublicUtilities(newPu);
            }
            if(fuelOil != null) {
                publicUtilities newPu = new publicUtilities(email, "fuelOil");
                puService.addPublicUtilities(newPu);
            }
            Cookie cookie = new Cookie("cookieEmail", email);
            response.addCookie(cookie);
            return ("success");
        }
   }

}
