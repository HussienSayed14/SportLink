package com.SportsLink.fields;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sportsLink/api/v1/fields")
@RequiredArgsConstructor
@Tag(name = "Fields" , description = "Apis That is Responsible Field Creation, Edit and searching")
public class FieldController {


}
