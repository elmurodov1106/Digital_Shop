package com.example.digital_shop.service.role;

import com.example.digital_shop.domain.dto.RoleDto;
import com.example.digital_shop.entity.user.RoleEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.RoleRepository;
import com.example.digital_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleEntity save(RoleDto roleDto){
        RoleEntity role = modelMapper.map(roleDto,RoleEntity.class);
        return roleRepository.save(role);
    }

    public RoleEntity update(RoleDto roleDto, UUID id){
        RoleEntity role = roleRepository.findById(id).
                orElseThrow( () -> new DataNotFoundException("role not found"));
        modelMapper.map(roleDto,role);
        return roleRepository.save(role);
    }

    public void deleteById(UUID id){
        roleRepository.deleteById(id);
    }

    public List<RoleEntity> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return roleRepository.findAll(pageable).getContent();
    }




}
