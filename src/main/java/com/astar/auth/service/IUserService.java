package com.astar.auth.service;

import com.astar.auth.model.entity.*;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public interface IUserService {

    List<User>  getAllUsers();

    Map<String, Object> getRentDetails(String userId);


    List<Map<String, Object>> getAllUsersRentDetails();


    void upsertUserRentDetail(String userId, RentDetails.Transaction transaction , Long rentAmount);


    void updateUser(String userId, User updatedUser);

     void deleteUser(String userId);
}
