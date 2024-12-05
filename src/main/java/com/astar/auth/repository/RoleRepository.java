package com.astar.auth.repository;

import com.astar.auth.model.entity.Role;
import com.astar.auth.model.enums.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(UserRole name);
}
