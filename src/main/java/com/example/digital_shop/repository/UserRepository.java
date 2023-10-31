package com.example.digital_shop.repository;

import com.example.digital_shop.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
   UserEntity findByEmail(String email);
   UserEntity findUserEntityByEmail(String fullName);
   Optional<UserEntity> findUserEntityByEmailEquals(String email);
   @Query("select id from users where email=:email")
   UUID findId(@Param("email")String email);
   @Query("select u from users u where u.email=:email")
   UserEntity findUser(@Param("email")String email);
}
