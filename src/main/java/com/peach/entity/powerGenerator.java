package com.peach.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class powerGenerator {
    private String email;
    private Integer power_generator_num;
    private Integer monthly_kwh;
    private Integer storage_kwh;
    private String generation_type;
}
