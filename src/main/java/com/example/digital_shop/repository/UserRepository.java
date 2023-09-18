package com.example.digital_shop.repository;

import com.example.digital_shop.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

//    Optional<UserEntity> findUserEntityByUsername(String fullName);
}
