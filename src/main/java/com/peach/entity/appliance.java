package com.peach.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class appliance {
    private String email;
    private Integer appliance_num;
    private Integer btu;
    private String model_name;
    private String manufacturer_name;
}
