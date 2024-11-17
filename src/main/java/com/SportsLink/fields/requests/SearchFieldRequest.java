package com.SportsLink.fields.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFieldRequest {
    private String fieldName;
    private Integer governorateId;
    private Integer cityId;
    private Integer districtId;
    private float minPrice;
    private float maxPrice;
}
