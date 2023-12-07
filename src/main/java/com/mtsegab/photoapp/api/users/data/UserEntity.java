package com.mtsegab.photoapp.api.users.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -2731425678149216053L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length=50)
    private String firstName;

    @Column(nullable = false, length=50)
    private String lastName;

    @Column(nullable = false, length=120, unique = false)
    private String email;

    @Column(nullable = false, unique = false)
    private String userId;

    @Column(nullable = false, unique = false)
    private String encryptedPassword = "00000";


}
