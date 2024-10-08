package com.SportsLink.userAuthentication.forgotPassword;

import com.SportsLink.userAuthentication.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordModel, String > {

    @Query("SELECT r FROM ForgotPasswordModel r WHERE r.user_id =:user")
    ForgotPasswordModel getUserForgotPasswordByUserId(UserModel user);

    @Query(value = "SELECT * FROM forgot_password  WHERE user_id =:userId", nativeQuery = true)
    ForgotPasswordModel getUserForgotPasswordByUserIdInt(int userId);

}
