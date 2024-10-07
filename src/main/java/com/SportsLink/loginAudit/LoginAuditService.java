package com.SportsLink.loginAudit;

import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.utils.DateTimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginAuditService {

    private final LoginAuditRepository loginAuditRepository;
    private final DateTimeService dateTimeService;
    private static final Logger logger = LoggerFactory.getLogger(LoginAuditService.class);


    public void createSuccessLoginAudit(UserModel user, String sessionId,String ipAddress, String description){
        try{
            LoginAuditModel record = LoginAuditModel.builder()
                    .is_success(true)
                    .session_id(sessionId)
                    .description(description)
                    .ip_address(ipAddress)
                    .login_time(dateTimeService.getCurrentTime())
                    .login_timestamp(dateTimeService.getCurrentTimestamp())
                    .login_date(dateTimeService.getCurrentDate())
                    .logout_time(null)
                    .user_id(user)
                    .build();
            loginAuditRepository.save(record);

        }catch (Exception e){
            logger.error("An Error happened while creating login audit for user ID: "+ user.getUser_id() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void createFailedLoginAudit(UserModel user,String description){
        try{
            LoginAuditModel record = LoginAuditModel.builder()
                    .is_success(false)
                    .session_id(null)
                    .description(description)
                    .ip_address(null)
                    .login_time(dateTimeService.getCurrentTime())
                    .login_timestamp(dateTimeService.getCurrentTimestamp())
                    .login_date(dateTimeService.getCurrentDate())
                    .logout_time(null)
                    .user_id(user)
                    .build();
            loginAuditRepository.save(record);

        }catch (Exception e){
            logger.error("An Error happened while creating login audit for user ID: "+ user.getUser_id() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
