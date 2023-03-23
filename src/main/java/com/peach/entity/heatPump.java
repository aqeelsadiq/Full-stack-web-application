package com.peach.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class heatPump {
    private String email;
    private Integer appliance_num;
    private Double seer;
    private Double hspf;
}
