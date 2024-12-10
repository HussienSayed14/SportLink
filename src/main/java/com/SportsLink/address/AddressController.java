package com.SportsLink.address;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sportsLink/api/v1/address")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@Tag(name = "Address", description = "Apis That is Responsible Address retrieval")
public class AddressController {
    private final AddressService addressService;

    // Endpoint to get all governorates
    @GetMapping("/governorates")
    public List<AddressProjection> getAllGovernorates(@RequestHeader(value = "Accept-Language", defaultValue = "en")
                                                      String language) {
        return addressService.getAllGovernorates(language);
    }

    // Endpoint to get all cities in a specific governorate
    @GetMapping("/governorates/{governorateId}/cities")
    public List<AddressProjection> getCitiesInGovernorate(@RequestHeader(value = "Accept-Language", defaultValue = "en")
                                                          String language,
                                                          @PathVariable int governorateId) {
        return addressService.getCitiesInGovernorate(language, governorateId);
    }

    // Endpoint to get all districts in a specific city
    @GetMapping("/cities/{cityId}/districts")
    public List<AddressProjection> getDistrictsInCity(@RequestHeader(value = "Accept-Language", defaultValue = "en")
                                                      String language,
                                                      @PathVariable int cityId) {
        return addressService.getDistrictsInCity(language, cityId);
    }
}
