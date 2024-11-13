package com.SportsLink.fields;

import com.SportsLink.address.CityModel;
import com.SportsLink.address.DistrictModel;
import com.SportsLink.address.GovernoratesModel;
import com.SportsLink.fields.dtos.FieldSearchResult;
import com.SportsLink.fields.requests.CreateFieldRequest;
import com.SportsLink.fields.requests.SearchFieldRequest;
import com.SportsLink.fields.responses.SearchFieldResponse;
import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.userAuthentication.login.LoginService;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final MessageService messageService;
    private final FieldRepository fieldRepository;
    private final EntityManager entityManager;

    public ResponseEntity<GenericResponse> createField(CreateFieldRequest request) {
        GenericResponse response = new GenericResponse();
        try {
            FieldModel field = new FieldModel();

            field.setFieldNameEn(request.getFieldNameEn());
            field.setFieldNameAr(request.getFieldNameAr());
            field.setLandMarkEn(request.getLandMarkEn());
            field.setLandMarkAr(request.getLandMarkAr());
            field.setHourPrice(request.getHourPrice());
            field.setActive(true);
            field.setBlocked(false);
            field.setVerified(true);
            field.setGoogleMapsLocation(request.getGoogleMapsLocation());

            // Use EntityManager to get proxy references by ID without loading them from the database
            field.setGovernorate(entityManager.getReference(GovernoratesModel.class, request.getGovernorateId()));
            field.setCity(entityManager.getReference(CityModel.class, request.getCityId()));

            // Set District only if districtId is provided in the request
            if (request.getDistrictId() != null) {
                field.setDistrict(entityManager.getReference(DistrictModel.class, request.getDistrictId()));
            } else {
                field.setDistrict(null);
            }

            if (request.getFieldOwnerId() != null) {
                field.setFieldOwner(entityManager.getReference(UserModel.class, request.getFieldOwnerId()));
            } else {
                field.setFieldOwner(null);
            }
            fieldRepository.save(field);
            response.setSuccessful(messageService.getMessage("generic.success"));

        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while creating Field: "+ request.getFieldNameEn() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();

        }

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    public ResponseEntity<GenericResponse> searchField(SearchFieldRequest request,String language) {
        SearchFieldResponse response = new SearchFieldResponse();
        try{
            Specification<FieldModel> spec = FieldSpecification.searchFields(request);
            List<FieldModel>  searchResult = fieldRepository.findAll(spec);

            searchResult.stream().map(field -> mapToDto(field, language)).collect(Collectors.toList());



            response.setSuccessful(messageService.getMessage("generic.success"));
            //response.setFields(searchResult);

        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while searching for Field \n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    private FieldSearchResult mapToDto(FieldModel field, String language) {
        FieldSearchResult response = new FieldSearchResult();

        // Set basic field properties
        response.setFieldId(field.getField_id());
        response.setFieldName(language.equals("ar") ? field.getFieldNameAr() : field.getFieldNameEn());
        response.setLandMark(language.equals("ar") ? field.getLandMarkAr() : field.getLandMarkEn());
        response.setHourPrice(field.getHourPrice());
        response.setGoogleMapsLocation(field.getGoogleMapsLocation());
        response.setActive(field.isActive());
        response.setVerified(field.isVerified());

        // Set location names based on the preferred language
        if (field.getGovernorate() != null) {
            response.setGovernorateName(language.equals("ar") ? field.getGovernorate().getName_ar() : field.getGovernorate().getName_en());
        }
        if (field.getCity() != null) {
            response.setCityName(language.equals("ar") ? field.getCity().getNameAr() : field.getCity().getNameEn());
        }
        if (field.getDistrict() != null) {
            response.setDistrictName(language.equals("ar") ? field.getDistrict().getName_ar() : field.getDistrict().getName_en());
        }

        return response;
    }
}
