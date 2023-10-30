package com.nttdata.user.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "phones")
public class Phone {
    @Id
    private String number;
    private String cityCode;
    private String countryCode;
    @ManyToOne // Many phones can belong to one user
    @JoinColumn(name = "userId", referencedColumnName="id")
    private User user;
}

