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

    @Transactional
    public UserEntity save(UserCreatDto userCreatDto) {
        if(checkUserEmail(userCreatDto.getEmail())){
            return null;
        }
        UserEntity userEntity = modelMapper.map(userCreatDto, UserEntity.class);
        userEntity.setState(UserState.UNVERIFIED);
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
        return userEntity;
    }

    @Override
    public Boolean verify(String sendingCode,UUID userId) {
        VerificationCode verificationCode=verificationCodeRepository.findVerificationCodeByUserId(userId)
                .orElseThrow(()->new DataNotFoundException("Verification code for this user does not exists"));
        if (Objects.equals(sendingCode, verificationCode.getSendingCode())
                && LocalDateTime.now().isBefore(verificationCode.getExpiryDate())) {
            UserEntity user = verificationCode.getUser();
            user.setState(UserState.ACTIVE);
            userRepository.save(user);
            return true;
        }
        throw new AuthenticationFailedException("Code is incorrect or Code is ragged");
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
        SellerInfo sellerInfo1 = checkPassport(sellerDto.getPassportNumber());
        if(checkUserEmail(sellerDto.getEmail())){
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
        if(sellerInfo1==null){
            SellerInfo sellerInfo=new SellerInfo
                    (sellerDto.getLastName(),
                            sellerDto.getFatherName(),
                            sellerDto.getBirthDate()
                            ,sellerDto.getPassportNumber()
                            ,save
                            ,sellerDto.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            sellerRepository.save(sellerInfo);
        }else {
            return null;
        }
        VerificationCode verificationCode=generateVerificationCode.generateVerificationCode(save);
        mailService.sendVerificationCode(save.getEmail(),verificationCode.getSendingCode());
      return save;
    }

    @Override
    public UserEntity updateUser(UserCreatDto userCreatDto, UUID userId) {
        UserEntity user1 = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        UserEntity user=modelMapper.map(userCreatDto,UserEntity.class);
        user.setId(userId);
        user.setState(user1.getState());
        user.setRole(user1.getRole());
        user.setCreatedDate(user1.getCreatedDate());
       return userRepository.save(user);
    }

    @Override
    public UUID getIdByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user.getId();
    }

    public UserEntity getById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("UserNot Found"));
    }

    private Boolean checkUserEmail(String email) {
        return userRepository.findUserEntityByEmail(email) != null;
    }
    public SellerInfo checkPassport(String passport){
        return sellerRepository.findSellerInfoByPassportNumberEquals(passport);
    }
    public RoleEntity checkRole(String name){
        return roleRepository.findRoleEntityByNameEqualsIgnoreCase(name);
    }
}
