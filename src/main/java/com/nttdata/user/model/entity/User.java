package com.nttdata.user.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    
    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private String token;
    @OneToMany // One user can have many phones
    private List<Phone> phones;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private boolean isActive;
}
