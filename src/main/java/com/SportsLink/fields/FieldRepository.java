package com.SportsLink.fields;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FieldRepository extends JpaRepository<FieldModel, Integer>, JpaSpecificationExecutor {
}
