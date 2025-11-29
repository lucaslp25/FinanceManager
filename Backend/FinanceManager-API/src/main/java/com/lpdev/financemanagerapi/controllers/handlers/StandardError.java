package com.lpdev.financemanagerapi.controllers.handlers;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardError implements Serializable {

    private String name;
    private String message;
    private HttpStatus status;
    private Instant timestamp;
    private String path;
}
