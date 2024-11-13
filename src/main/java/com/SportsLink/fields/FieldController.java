package com.SportsLink.fields;


import com.SportsLink.fields.requests.CreateFieldRequest;
import com.SportsLink.utils.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sportsLink/api/v1/fields")
@RequiredArgsConstructor
@Tag(name = "Fields" , description = "Apis That is Responsible Field Creation, Edit and searching")
public class FieldController {

    private final FieldService fieldService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    @PostMapping("/createField")
    ResponseEntity<GenericResponse> createField(@Valid @RequestBody CreateFieldRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return fieldService.createField(request);
    }


}
