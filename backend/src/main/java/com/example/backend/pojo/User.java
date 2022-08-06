package com.example.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "password")
public class User {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String phoneNumber;
    private Integer admin;
    private Integer status;
    private Integer sex;
    private Integer age;
    private String address;
}
