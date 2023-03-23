package com.peach.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class heater {
    private String email;
    private Integer appliance_num;
    private String energy_source;
}
