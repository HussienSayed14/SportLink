package com.SportsLink.fields.responses;

import com.SportsLink.fields.FieldModel;
import com.SportsLink.fields.dtos.FieldSearchResult;
import com.SportsLink.utils.GenericResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchFieldResponse extends GenericResponse {
    List<FieldSearchResult> fields;
}
