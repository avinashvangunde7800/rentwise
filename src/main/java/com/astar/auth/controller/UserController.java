package com.astar.auth.controller;

import com.astar.auth.model.entity.User;
import com.astar.auth.model.entity.RentDetails.*;
import com.astar.auth.model.response.ErrorResponse;
import com.astar.auth.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>  getAllUsers(){
        try{
            Map<String,Object> response =new HashMap<>();
            response.put("data",iUserService.getAllUsers());
            response.put("meta",null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            log.error("Error in /user/getAllUsers",ex);
            ErrorResponse response = new ErrorResponse();
            response.setStatus(ErrorResponse.STATUS.FAILED);
            response.setMsg(ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/rent-details/{userId}")
    @PreAuthorize("hasRole('USER') or " + "hasRole('ADMIN')")
    public ResponseEntity<?> getRentDetails(@PathVariable String userId) {
        try{
            Map<String,Object> response =new HashMap<>();
            response.put("data",iUserService.getRentDetails(userId));
            response.put("meta",null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            log.error("Error in /user//user-details/{userId}",userId,ex);
            ErrorResponse response = new ErrorResponse();
            response.setStatus(ErrorResponse.STATUS.FAILED);
            response.setMsg(ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAll/rent-details")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsersRentDetails() {
        try{
            Map<String,Object> response =new HashMap<>();
            response.put("data",iUserService.getAllUsersRentDetails());
            response.put("meta",null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            log.error("Error in /admin/users",ex);
            ErrorResponse response = new ErrorResponse();
            response.setStatus(ErrorResponse.STATUS.FAILED);
            response.setMsg(ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/add/rent-detail/{userid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>  upsertUserRentDetail( @PathVariable String userid,
                                                    @RequestBody Transaction transaction,
                                @RequestParam(value = "rentAmount", required = false) Long rentAmount) {
        try{
            iUserService.upsertUserRentDetail(userid, transaction ,rentAmount);
            return new ResponseEntity<>("Upsert Successful", HttpStatus.OK);
        }catch (Exception ex) {
            log.error("Error in /admin/upsert/{userId}",ex);
            ErrorResponse response = new ErrorResponse();
            response.setStatus(ErrorResponse.STATUS.FAILED);
            response.setMsg(ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update-user/{userid}")
    @PreAuthorize("hasRole('USER') or " + "hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable String userid, @RequestBody User user) {
        try{
            iUserService.updateUser(userid, user);
            return new ResponseEntity<>("User update successful!", HttpStatus.OK);
        }catch (Exception ex) {
            log.error("Error in /user/update-user/{id}",userid,ex);
            ErrorResponse response = new ErrorResponse();
            response.setStatus(ErrorResponse.STATUS.FAILED);
            response.setMsg(ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/delete-user/{userid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>  deleteUser(@PathVariable String userid ) {
        try{
            iUserService.deleteUser(userid);
            return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
        }catch (Exception ex) {
            log.error("Error in /user/delete-user/{id}",ex);
            ErrorResponse response = new ErrorResponse();
            response.setStatus(ErrorResponse.STATUS.FAILED);
            response.setMsg(ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
