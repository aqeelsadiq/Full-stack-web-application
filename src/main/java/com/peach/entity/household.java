package com.peach.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class household {
    private String email;
    private String household_type;
    private Integer heating;
    private Integer cooling;
    private Integer square_footage;
    private String postalCode;
}
