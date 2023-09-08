package com.globalLogic.usermircroservice.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Phone {
    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id; 

    @NotNull
    private Long number;

    @NotNull
    private Integer citycode;

    @NotBlank
    private String countrycode;

   @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
