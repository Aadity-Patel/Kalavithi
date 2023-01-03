package com.tw.prograd.likes;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikesDTO {

    @NotNull
    @NotEmpty
    private Integer userId;

    @NotNull
    @NotEmpty
    private Integer imageId;
}
