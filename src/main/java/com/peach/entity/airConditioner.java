package com.peach.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class airConditioner {
    private String email;
    private Integer appliance_num;
    private Double eer;
}
