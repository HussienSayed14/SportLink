package com.SportsLink.fields;


import com.SportsLink.fields.requests.CreateFieldRequest;
import com.SportsLink.fields.requests.SearchFieldRequest;
import com.SportsLink.utils.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sportsLink/api/v1/fields")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@Tag(name = "Fields" , description = "Apis That is Responsible Field Creation, Edit and searching")
public class FieldController {

    private final FieldService fieldService;

//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    @PostMapping(value = "/createField")
    ResponseEntity<GenericResponse> createField(@Valid @RequestBody CreateFieldRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return fieldService.createField(request);
    }

    @PostMapping("/search")
    ResponseEntity<GenericResponse> createField(@Valid @RequestBody SearchFieldRequest request,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage,
                                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return fieldService.searchField(request,acceptLanguage);
    }

    @PostMapping("/followField/{fieldId}")
    ResponseEntity<GenericResponse> followField(@PathVariable int fieldId, HttpServletRequest httpServletRequest){
        return fieldService.followField(fieldId, httpServletRequest);
    }

    @PostMapping("/unfollowField/{fieldId}")
    ResponseEntity<GenericResponse> unfollowField(@PathVariable int fieldId, HttpServletRequest httpServletRequest){
        return fieldService.unfollowField(fieldId, httpServletRequest);
    }

    @GetMapping("/is-following/{fieldId}")
    ResponseEntity<Boolean> isFollowing(@PathVariable int fieldId, HttpServletRequest httpServletRequest){
        return fieldService.isFollowingField(fieldId, httpServletRequest);
    }




}
