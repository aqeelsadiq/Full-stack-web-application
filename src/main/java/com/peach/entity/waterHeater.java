package com.peach.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class waterHeater {
    private String email;
    private Integer appliance_num;
    private String energy_source;
    private Double capacity;
    private Integer current_temperature_setting;
}
