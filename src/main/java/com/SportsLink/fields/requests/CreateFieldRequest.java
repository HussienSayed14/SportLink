package com.SportsLink.fields.requests;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFieldRequest {
    @Min(value = 1, message = "Id must be larger than 1")
    @NotNull(message = "Field owner ID is required")
    private Integer fieldOwnerId;

    @NotBlank(message = "Field name in English is required")
    @Size(max = 100, message = "Field name in English cannot exceed 100 characters")
    private String fieldNameEn;

    @NotBlank(message = "Field name in Arabic is required")
    @Size(max = 100, message = "Field name in Arabic cannot exceed 100 characters")
    private String fieldNameAr;

    @Size(max = 150, message = "Landmark in English cannot exceed 150 characters")
    private String landMarkEn;

    @Size(max = 150, message = "Landmark in Arabic cannot exceed 150 characters")
    private String landMarkAr;

    @NotNull(message = "Hour price is required")
    @Positive(message = "Hour price must be positive")
    private Float hourPrice;

    @NotBlank(message = "Google Maps location is required")
    @Size(max = 255, message = "Google Maps location cannot exceed 255 characters")
    private String googleMapsLocation;

    @NotNull(message = "Governorate ID is required")
    private Integer governorateId;

    @NotNull(message = "City ID is required")
    private Integer cityId;

    private Integer districtId;

}
