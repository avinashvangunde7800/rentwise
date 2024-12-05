package com.astar.auth.service.impl;

import com.astar.auth.model.entity.*;
import com.astar.auth.model.entity.RentDetails.Transaction;
import com.astar.auth.repository.*;
import com.astar.auth.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private RentInfoRepository rentInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User>  getAllUsers(){
        return userRepository.findAll();
    }


    public Map<String, Object> getRentDetails(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        RentDetails rentDetails = rentInfoRepository.findByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("userId", user.getId());
        result.put("userName", user.getUsername());
        result.put("email", user.getEmail());
        result.put("mobile", user.getMobile());
        result.put("address", user.getAddress());

        if (rentDetails != null) {
            result.put("startDate", rentDetails.getStartDate());
            result.put("rent", rentDetails.getRent());
        } else {
            result.put("startDate", null);
            result.put("rent", Collections.emptyList());
        }

        return result;
    }




    @Override
    public List<Map<String, Object>> getAllUsersRentDetails() {

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            RentDetails rentDetails = rentInfoRepository.findByUserId(user.getId());

            Map<String, Object> result = new HashMap<>();
            result.put("userId", user.getId());
            result.put("userName", user.getUsername());
            result.put("email", user.getEmail());
            result.put("mobile", user.getMobile());
            result.put("address", user.getAddress());
            result.put("startDate", rentDetails != null ? rentDetails.getStartDate() : null);

            List<Transaction> transactions = rentDetails != null ? rentDetails.getRent() : new ArrayList<>();
            result.put("rent", transactions);
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public void upsertUserRentDetail(String userId, Transaction transaction ,Long rentAmount) {
        RentDetails rentDetails = rentInfoRepository.findByUserId(userId);

        if (rentDetails == null) {
            rentDetails = new RentDetails();
            rentDetails.setUserId(userId);
            rentDetails.setStartDate(new Date());
            rentDetails.setAmount(rentAmount);
            rentDetails.setRent(new ArrayList<>());
            rentDetails.getRent().add(transaction);
        } else {
            if (rentAmount != null)
                rentDetails.setAmount(rentAmount);
            rentDetails.getRent().add(transaction);
        }

        rentInfoRepository.save(rentDetails);
    }


    public void updateUser(String userId, User updatedUser) {
        User existingUser = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with id: " + userId));
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setMobile(updatedUser.getMobile());
        existingUser.setEmail(updatedUser.getEmail());
        userRepository.save(existingUser);
    }


    public void deleteUser(String userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

}
