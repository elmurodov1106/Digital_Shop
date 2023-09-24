package com.example.digital_shop.service.user;

import com.example.digital_shop.domain.dto.LoginDto;
import com.example.digital_shop.domain.dto.SellerDto;
import com.example.digital_shop.domain.dto.UserCreatDto;
import com.example.digital_shop.entity.user.UserEntity;

import java.util.UUID;


public interface UserService {
    UserEntity save(UserCreatDto userCreatDto);
    Boolean verify(String sendingCode,UUID userId);
    UserEntity getNewVerifyCode(String email);
    UserEntity signIn(LoginDto loginDto);
    UserEntity saveSeller(SellerDto sellerDto);
    UserEntity updateUser(UserCreatDto userCreatDto, UUID userId);
    UUID getIdByEmail(String email);

    UserEntity getById(UUID userId);

}
