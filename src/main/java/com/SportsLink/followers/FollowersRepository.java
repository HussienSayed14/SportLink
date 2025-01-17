package com.SportsLink.followers;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowersRepository extends JpaRepository<FollowerModel,Integer> {

    @Query(value = "SELECT COUNT(f.id) FROM FollowerModel f WHERE f.field.id =:fieldId")
    int findFollowersByFieldId(int fieldId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM FollowerModel f " +
            "WHERE f.user.user_id = :userId AND f.field.id = :fieldId")
    boolean isUserFollowingField(int userId, int fieldId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM FollowerModel f WHERE f.user.user_id =:userId AND f.field.id =:fieldId")
    void deleteFieldFollow(int fieldId, int userId);
}
