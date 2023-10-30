package com.nttdata.user.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import jakarta.persistence.*;

@Data
@Builder
public class UserResponse {
   
    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
}
