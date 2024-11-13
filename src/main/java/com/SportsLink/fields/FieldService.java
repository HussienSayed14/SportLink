package com.SportsLink.fields;

import com.SportsLink.address.CityModel;
import com.SportsLink.address.DistrictModel;
import com.SportsLink.address.GovernoratesModel;
import com.SportsLink.fields.requests.CreateFieldRequest;
import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.userAuthentication.login.LoginService;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
