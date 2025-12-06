package com.psa.SpringbootPsa.dto;

import lombok.*;

import java.time.Instant;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T>{
    private boolean success;
    private String message;
    private T data;
    private String code;

    @Builder.Default
    private Instant timestamp = Instant.now();
}
