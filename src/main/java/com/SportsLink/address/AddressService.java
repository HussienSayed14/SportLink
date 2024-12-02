package com.SportsLink.address;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    @Cacheable(value = "governorates", key = "#language")
    public List<AddressProjection> getAllGovernorates(String language) {
        return addressRepository.getAllGovernorates(language);
    }

    @Cacheable(value = "cities", key = "#language + '_' + #governorateId")
    public List<AddressProjection> getCitiesInGovernorate(String language, int governorateId) {
        return addressRepository.getCitiesInGovernorate(language, governorateId);
    }

    @Cacheable(value = "districts", key = "#language + '_' + #cityId")
    public List<AddressProjection> getDistrictsInCity(String language, int cityId) {
        return addressRepository.getDistrictsInCity(language, cityId);
    }


}
