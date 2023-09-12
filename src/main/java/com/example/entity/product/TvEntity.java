package com.example.entity.product;

import com.example.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tv")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TvEntity extends BaseEntity {
    private Double isSmart;
    private Integer Size;
    private Integer ScreenSpeed;
    private String model;
    private String name;
    private Double cost;
}
