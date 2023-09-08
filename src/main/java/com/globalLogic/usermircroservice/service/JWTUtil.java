package com.globalLogic.usermircroservice.service;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {

    // Genera una clave de firma segura para HS512
    public static SecretKey generateHS512SecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}

