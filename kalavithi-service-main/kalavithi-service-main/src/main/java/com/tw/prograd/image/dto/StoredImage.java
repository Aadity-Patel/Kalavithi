package com.tw.prograd.image.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoredImage {
    private List<Image> images;
}


