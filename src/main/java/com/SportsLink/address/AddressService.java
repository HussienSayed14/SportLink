package com.SportsLink.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;


    public List<AddressResponseDto> getAllGovernorates(String language) {
        return addressRepository.getAllGovernorates(language);
    }

    public List<AddressResponseDto> getCitiesInGovernorate(String language, int governorateId) {
        return addressRepository.getCitiesInGovernorate(language, governorateId);
    }

    public List<AddressResponseDto> getDistrictsInCity(String language, int cityId) {
        return addressRepository.getDistrictsInCity(language, cityId);
    }
}
