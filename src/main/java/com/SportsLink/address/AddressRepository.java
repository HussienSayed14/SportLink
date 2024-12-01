package com.SportsLink.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<DistrictModel, Integer> {


    @Query(value = "SELECT g.governorate_id AS id, CASE WHEN :language = 'ar' THEN g.name_ar ELSE g.name_en END AS name " +
            "FROM governorate g", nativeQuery = true)
    List<AddressProjection> getAllGovernorates(String language);

    @Query(value = "SELECT c.city_id AS id, CASE WHEN :language = 'ar' THEN c.name_ar ELSE c.name_en END AS name " +
            "FROM city c WHERE c.governorate_id = :governorateId", nativeQuery = true)
    List<AddressProjection> getCitiesInGovernorate(String language, int governorateId);

    @Query(value = "SELECT d.district_id AS id, CASE WHEN :language = 'ar' THEN d.name_ar ELSE d.name_en END AS name " +
            "FROM district d WHERE d.city_id = :cityId", nativeQuery = true)
    List<AddressProjection> getDistrictsInCity(String language, int cityId);



}
