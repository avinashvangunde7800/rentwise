package com.astar.auth.model.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String msg;
    private STATUS status;

    public enum STATUS {
        FAILED, ERROR
    }
}
