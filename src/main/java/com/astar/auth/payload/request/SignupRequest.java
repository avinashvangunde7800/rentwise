package com.astar.auth.payload.request;

import lombok.Data;
import java.util.Set;
import javax.validation.constraints.*;

@Data
public class SignupRequest {
    @Size(min = 3, max = 20)
    private String username;
 
    @Size(max = 50)
    @Email
    private String email;

    @Size(min = 6, max = 40)
    private String password;

    private String address;

    private Long mobile;
    
    private Set<String> roles;
    

}
