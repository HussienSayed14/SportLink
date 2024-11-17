package com.SportsLink.fields.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldSearchResult {
    private int fieldId;
    private String fieldName;
    private String landMark;
    private float hourPrice;
    private String googleMapsLocation;
    private boolean isActive;
    private boolean isVerified;
    private double averageRating;
    private String governorateName;
    private String cityName;
    private String districtName;
    private int followersCount;
}
