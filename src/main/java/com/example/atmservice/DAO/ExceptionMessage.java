package com.example.atmservice.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String path;
}
