package com.example.digital_shop.entity.product;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "tv")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TvEntity extends ProductEntity {
    private Double isSmart;
    private Integer Size;
    private Integer ScreenSpeed;
    private String description;
}



