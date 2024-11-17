package com.SportsLink.followers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowersRepository extends JpaRepository<FollowerModel,Integer> {

    @Query(value = "SELECT COUNT(f.id) FROM FollowerModel f WHERE f.field.id =:fieldId")
    int findFollowersByFieldId(int fieldId);

}
