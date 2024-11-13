package com.SportsLink.fields;

import com.SportsLink.fields.requests.SearchFieldRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FieldSpecification {
    public static Specification<FieldModel> searchFields(SearchFieldRequest request) {
        return (Root<FieldModel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Fuzzy search for fieldName (both English and Arabic)
            if (request.getFieldName() != null && !request.getFieldName().isEmpty()) {
                String searchPattern = "%" + request.getFieldName() + "%"; // `%` allows partial matches
                Predicate fieldNameEn = cb.like(cb.lower(root.get("fieldNameEn")), searchPattern.toLowerCase());
                Predicate fieldNameAr = cb.like(cb.lower(root.get("fieldNameAr")), searchPattern.toLowerCase());
                predicates.add(cb.or(fieldNameEn, fieldNameAr)); // Match either English or Arabic name
            }

            // 2. Filter by price range
            if (request.getMinPrice() != 0) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("hourPrice"), request.getMinPrice()));
            }
            if (request.getMaxPrice() != 0) {
                predicates.add(cb.lessThanOrEqualTo(root.get("hourPrice"), request.getMaxPrice()));
            }

            // 3. Filter by location based on availability of district_id, city_id, or governorate_id
            if (request.getDistrictId() != null) {
                // If district_id is provided, use only district_id for filtering
                predicates.add(cb.equal(root.get("district").get("district_id"), request.getDistrictId()));
            } else if (request.getCityId() != null) {
                // If city_id is provided but no district_id, use only city_id for filtering
                predicates.add(cb.equal(root.get("city").get("id"), request.getCityId()));
            } else if (request.getGovernorateId() != null) {
                // If only governorate_id is provided, use it for filtering
                predicates.add(cb.equal(root.get("governorate").get("id"), request.getGovernorateId()));
            }

            // 4. Filter unblocked fields only
            predicates.add(cb.isFalse(root.get("isBlocked")));

            return cb.and(predicates.toArray(new Predicate[0])); // Combine all predicates with AND
        };
    }
}
