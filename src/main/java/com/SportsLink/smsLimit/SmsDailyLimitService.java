package com.SportsLink.smsLimit;

import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.utils.DateTimeService;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SmsDailyLimitService {

    private final SmsDailyLimitRepository smsDailyLimitRepository;
    private final DateTimeService dateTimeService;
    private static final Logger logger = LoggerFactory.getLogger(SmsDailyLimitService.class);


    public boolean canSendSms(UserModel user, String type, int dailyLimit){
        try{
            SmsDailyLimitModel limitRecord = smsDailyLimitRepository.getUserDailyLimit(user,type);

            if(limitRecord == null){
                SmsDailyLimitModel newLimit = SmsDailyLimitModel.builder()
                        .daily_attempts(dailyLimit)
                        .last_created(dateTimeService.getCurrentTimestamp())
                        .user_attempts(0)
                        .user_id(user)
                        .type(type)
                        .build();
                smsDailyLimitRepository.save(newLimit);
                return true;
            }

            // Check if it's a new day, reset the count if so
            if (limitRecord.getLast_created().before(dateTimeService.getCurrentTimestamp())){
                limitRecord.setUser_attempts(0);
                limitRecord.setLast_created(dateTimeService.getCurrentTimestamp());
                smsDailyLimitRepository.save(limitRecord);
                return true;
            }

            if(limitRecord.getUser_attempts() >= limitRecord.getDaily_attempts()){
                return false;
            }
            return true;


        }catch (Exception e){
            logger.error("An Error happened while creating daily limit: "+ user.getPhone_number() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }



    }
        public void incrementSmsDailyLimit(UserModel user, String type){
            smsDailyLimitRepository.incrementSmsDailyLimit(user,type);
        }
}
