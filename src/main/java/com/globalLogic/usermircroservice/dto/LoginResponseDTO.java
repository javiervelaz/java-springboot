package com.globalLogic.usermircroservice.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class LoginResponseDTO {
    public UUID id;
    public Date created;
    public Date lastLogin;
    public String token;
    public boolean isActive;
    public String name;
    public String email;
    public String password; 
    public List<PhonesDTO> phones;
}
