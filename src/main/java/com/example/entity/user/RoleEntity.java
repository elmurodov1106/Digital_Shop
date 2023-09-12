package com.example.entity.user;

import com.example.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleEntity extends BaseEntity {
    @Column(unique = true)
    private String name;
//    @ManyToMany(cascade = CascadeType.ALL)
//    private List<PermissionEntity> permissions;
}
