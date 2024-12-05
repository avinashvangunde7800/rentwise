package com.astar.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    @Size(max = 20)
    private String username;

    @Email
    private String email;

    @Size(max = 20)
    private String password;

    private String address;

    private Long mobile;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password , String address , Long mobile) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.mobile = mobile;
    }
}