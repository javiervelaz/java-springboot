package com.globalLogic.usermircroservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {


    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; 

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Email(message = "El correo debe tener un formato válido (aaaaaaa@undominio.algo)")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d.*\\d)[a-zA-Z\\d]{8,12}$", message = "La clave debe tener una mayúscula y dos números, longitud entre 8 y 12")
    private String password;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LOGIN_DATE")
    private Date lastLogin;

    @Column(name  = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "TOKEN")
    private String token;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Phone> phones = new ArrayList<>();

    public User orElse() {
        return null;
    }

    

    
}
