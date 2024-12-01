package com.SportsLink.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;


    public List<AddressProjection> getAllGovernorates(String language) {
        return addressRepository.getAllGovernorates(language);
    }

    public List<AddressProjection> getCitiesInGovernorate(String language, int governorateId) {
        return addressRepository.getCitiesInGovernorate(language, governorateId);
    }

    public List<AddressProjection> getDistrictsInCity(String language, int cityId) {
        return addressRepository.getDistrictsInCity(language, cityId);
    }
}
