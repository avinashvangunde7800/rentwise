package com.astar.auth.repository;

import com.astar.auth.model.entity.RentDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RentInfoRepository extends MongoRepository<RentDetails, String>{
    RentDetails findByUserId(String userId);

}
