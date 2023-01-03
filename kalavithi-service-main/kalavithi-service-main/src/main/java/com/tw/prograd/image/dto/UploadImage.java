package com.tw.prograd.image.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadImage {
    private Integer id;
    private String name;
}
