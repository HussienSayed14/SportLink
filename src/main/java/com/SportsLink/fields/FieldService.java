package com.SportsLink.fields;

import com.SportsLink.fields.requests.CreateFieldRequest;
import com.SportsLink.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldService {
    public ResponseEntity<GenericResponse> createField(CreateFieldRequest request) {
        GenericResponse response = new GenericResponse();
        try {

        }catch (Exception e){

        }

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
