package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{
    @Column(name = "name", nullable = false, length = 50)
    private String name;

}
