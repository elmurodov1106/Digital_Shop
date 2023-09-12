package com.example.entity.seller;


import com.example.entity.BaseEntity;
import com.example.entity.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "seller_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellerInfo extends BaseEntity {
    private String lastName;
    private String fathersName;
    private LocalDate birthDate;
    @Column(unique = true,nullable = false)
    private String passportNumber;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(unique = true,nullable = false)
    private String phoneNumber;
}
