package com.astar.auth.model.entity;

import com.astar.auth.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "roles")
@AllArgsConstructor
public class Role {
    @Id
    private String id;

    private UserRole name;
}
