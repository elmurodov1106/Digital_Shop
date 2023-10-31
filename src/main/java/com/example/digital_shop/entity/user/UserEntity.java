package com.example.digital_shop.entity.user;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserEntity extends BaseEntity implements UserDetails {
   protected String name;
   @Column(unique = true,nullable = false)
   protected String email;
   @Column(nullable = false)
   protected String password;
    @ManyToOne(cascade = CascadeType.ALL)
    protected RoleEntity role;
    @Enumerated(EnumType.STRING)
    protected UserState state=UserState.UNVERIFIED;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String ROLE = "ROLE_";
        return List.of(new SimpleGrantedAuthority(ROLE+role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
