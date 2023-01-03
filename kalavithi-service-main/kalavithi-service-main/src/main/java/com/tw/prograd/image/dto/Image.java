package com.tw.prograd.image.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private Integer id;
    private String name;
    private String url;
    private String description;
}
