package com.tw.prograd.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String username;
    private Integer id;
}
