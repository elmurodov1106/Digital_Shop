package com.example.digital_shop.controller;

import com.example.digital_shop.domain.dto.RoleDto;
import com.example.digital_shop.entity.user.RoleEntity;
import com.example.digital_shop.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/add")
//    @PreAuthorize(value = "hasRole(ADMIN)")
    public ResponseEntity<RoleEntity> addRole(
            @RequestBody RoleDto roleDto
    ){
        return ResponseEntity.ok(roleService.save(roleDto));
    }

    @DeleteMapping("/{roleId}/delete")
//    @PreAuthorize("hasRole(ADMIN)")
    public ResponseEntity deleteGroup(
            @PathVariable UUID roleId
    ){
        roleService.deleteById(roleId);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{roleId}update")
//    @PreAuthorize("hasRole(ADMIN)")
    public ResponseEntity<RoleEntity> updateGroup(
            @PathVariable UUID roleId,
            @RequestBody RoleDto roleDto
    ){
        return ResponseEntity.ok(roleService.update(roleDto, roleId));
    }

    @GetMapping("/getAll")
//    @PreAuthorize("hasRole(SUPER_ADMIN)")
    public ResponseEntity<List<RoleEntity>> getAll(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size
    ){
        return ResponseEntity.status(200).body(roleService.getAll(page,size));
    }
}
