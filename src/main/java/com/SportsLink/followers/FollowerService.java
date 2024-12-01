package com.SportsLink.followers;

import com.SportsLink.fields.FieldModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FollowerService {
    private static final Logger logger = LoggerFactory.getLogger(FollowerService.class);
    private final FollowersRepository followersRepository;
    private final EntityManager entityManager;


    public boolean createFieldFollower(int fieldId, int userId){
        try {
            FollowerModel followerModel = new FollowerModel();
            followerModel.setUser(entityManager.getReference(UserModel.class, userId));
            followerModel.setField(entityManager.getReference(FieldModel.class,fieldId));
            followersRepository.save(followerModel);
            return true;
        }catch (Exception e){
            logger.error("An Error happened while creating following record for Field \n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteFieldFollower(int fieldId, int userId){
        try {
            followersRepository.deleteFieldFollow(fieldId, userId);
            return true;
        }catch (Exception e){
            logger.error("An Error happened while creating following record for Field \n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

}
