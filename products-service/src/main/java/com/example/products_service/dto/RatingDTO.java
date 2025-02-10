package com.example.products_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingDTO {
    private Double rate;
    private Integer count;
}
