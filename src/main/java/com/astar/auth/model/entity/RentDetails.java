package com.astar.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Data
@Document(collection = "user_details")
@AllArgsConstructor
@NoArgsConstructor
public class RentDetails {
    @Id
    private String id;
    private String userId;
    private Long amount;
    private Date startDate;
    private List<Transaction> rent;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction {
        private Date date;
        private String currency;
    }
}