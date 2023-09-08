package com.globalLogic.usermircroservice.dto;

import java.util.Date;
import java.util.UUID;

public class RegistrationResponseDTO {
    public UUID id;
    public Date created;
    public Date lastLogin;
    public String token;
    public boolean isActive;
    
}
