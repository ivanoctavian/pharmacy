package com.pharmacy.modules.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "Users_seq")
    @SequenceGenerator(name="Users_seq", sequenceName="Users_seq", allocationSize=1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
