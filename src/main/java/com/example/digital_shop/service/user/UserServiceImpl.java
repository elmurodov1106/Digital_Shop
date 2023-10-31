package com.example.digital_shop.service.user;

import com.example.digital_shop.domain.dto.LoginDto;
import com.example.digital_shop.domain.dto.SellerDto;
import com.example.digital_shop.domain.dto.UserCreatDto;
import com.example.digital_shop.entity.seller.SellerInfo;
import com.example.digital_shop.entity.user.RoleEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.entity.user.UserState;
import com.example.digital_shop.entity.verification.VerificationCode;
import com.example.digital_shop.exception.AuthenticationFailedException;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.RoleRepository;
import com.example.digital_shop.repository.SellerRepository;
import com.example.digital_shop.repository.UserRepository;
import com.example.digital_shop.repository.VerificationCodeRepository;
import com.example.digital_shop.service.mailservice.MailService;
import com.example.digital_shop.service.verificationcode.GenerateVerificationCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final SellerRepository sellerRepository;
    private final GenerateVerificationCode generateVerificationCode;

    @Override
    @Transactional
    public UUID save(UserCreatDto userCreatDto) {
        if(!checkUserEmail(userCreatDto.getEmail())){
            return null;
        }
        UserEntity userEntity = modelMapper.map(userCreatDto, UserEntity.class);
        userEntity.setState(UserState.UNVERIFIED);
        UserEntity userEntityByEmail = userRepository.findUserEntityByEmail(userEntity.getEmail());
        if(userEntityByEmail!=null){
            return null;
        }
        RoleEntity userRole = roleRepository.findRoleEntityByNameEqualsIgnoreCase("User");
        if (userRole != null) {
            VerificationCode verificationCode = generateVerificationCode.generateVerificationCode(userEntity);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRole(userRole);
            userRepository.save(userEntity);
            mailService.sendVerificationCode(userEntity.getEmail(), verificationCode.getSendingCode());
        }
        if (userRole == null) {
            RoleEntity save = roleRepository.save(new RoleEntity("User"));
            userEntity.setRole(save);
            VerificationCode verificationCode = generateVerificationCode.generateVerificationCode(userEntity);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);
            mailService.sendVerificationCode(userEntity.getEmail(), verificationCode.getSendingCode());
        }
        return userRepository.findId(userEntity.getEmail());
    }

    @Override
    public Boolean verify(String sendingCode,UUID userId) {
        Optional<VerificationCode> verificationCode=verificationCodeRepository.findVerificationCodeByUserId(userId);
        if ( verificationCode.isPresent()&&Objects.equals(sendingCode, verificationCode.get().getSendingCode())
                && LocalDateTime.now().isBefore(verificationCode.get().getExpiryDate())) {
            UserEntity user = verificationCode.get().getUser();
            user.setState(UserState.ACTIVE);
            userRepository.save(user);
            return true;
        }
        return null;
    }

    @Override
    @Transactional
    public UserEntity getNewVerifyCode(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
          verificationCodeRepository.deleteVerificationCodeByUserEmail(userEntity.getEmail());
        VerificationCode verificationCode = generateVerificationCode.generateVerificationCode(userEntity);
        mailService.sendVerificationCode(email, verificationCode.getSendingCode());
        return userEntity;
    }

    @Override
    public UserEntity signIn(LoginDto loginDto) {
        UserEntity user = userRepository.findUserEntityByEmail(loginDto.getEmail());
        if(user==null){
            return null;
        }
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            if (user.getState().equals(UserState.ACTIVE)) {
                return user;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public UserEntity saveSeller(SellerDto sellerDto) {
        RoleEntity role = checkRole("Seller");
        if(!checkUserEmail(sellerDto.getEmail())|| !checkPassport(sellerDto.getPassportNumber())|| !checkPhoneNumber(sellerDto.getPhoneNumber())){
            return null;
        }
        if(role==null){
            role = roleRepository.save(new RoleEntity("Seller"));
        }
        UserEntity user=
                new UserEntity(sellerDto.getName(),
                        sellerDto.getEmail(),
                        sellerDto.getPassword(),
                        role,
                        UserState.UNVERIFIED);
        UserEntity save = userRepository.save(user);
        SellerInfo sellerInfo = new SellerInfo
                (sellerDto.getLastName(),
                        sellerDto.getFatherName(),
                        sellerDto.getBirthDate()
                        , sellerDto.getPassportNumber()
                        , save
                        , sellerDto.getPhoneNumber(),0.0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sellerRepository.save(sellerInfo);
        VerificationCode verificationCode=generateVerificationCode.generateVerificationCode(save);
        mailService.sendVerificationCode(save.getEmail(),verificationCode.getSendingCode());
      return save;
    }

    @Override
    public UserEntity updateUser(String name, UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }

        return userRepository.save(user);
    }


    @Override
    public UUID getIdByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user.getId();
    }

    public UserEntity getById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User Not Found"));
    }

    private Boolean checkUserEmail(String email) {
        return userRepository.findUserEntityByEmail(email) == null;
    }
    public Boolean checkPassport(String passport){
        return sellerRepository.findSellerInfoByPassportNumberEquals(passport) == null;
    }
    public Boolean checkPhoneNumber(String phoneNumber){
        return sellerRepository.findSellerInfoByPhoneNumberEquals(phoneNumber) == null;
    }
    public RoleEntity checkRole(String name){
        return roleRepository.findRoleEntityByNameEqualsIgnoreCase(name);
    }
}
