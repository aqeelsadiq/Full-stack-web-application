package com.peach.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class postalCode {
    private String postal_code;
    private String city;
    private String state;
    private String latitude;
    private String longitude;
}
