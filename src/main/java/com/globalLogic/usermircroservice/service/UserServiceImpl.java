package com.globalLogic.usermircroservice.service;

import java.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.globalLogic.usermircroservice.dto.LoginResponseDTO;
import com.globalLogic.usermircroservice.dto.RegistrationResponseDTO;
import com.globalLogic.usermircroservice.dto.UserDTO;
import com.globalLogic.usermircroservice.exception.CustomException;
import com.globalLogic.usermircroservice.exception.ErrorDetail;
import com.globalLogic.usermircroservice.model.Phone;
import com.globalLogic.usermircroservice.model.User;
import com.globalLogic.usermircroservice.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${jwt.secret}") // Debes configurar esta propiedad en tu application.properties o application.yml
    private String jwtSecret;

    @Override
    public ResponseEntity<Object> registerUser(UserDTO dto) {
        // Realizar validaciones adicionales aquí si es necesario
        // Por ejemplo, verifica si el usuario ya existe
        if (checkIfExists(dto.email) != null) {
            throw new CustomException(new ErrorDetail("El usuario ya existe.", HttpStatus.BAD_REQUEST.value()));
        }
        User userEntity  = new User();
        userEntity.setEmail(dto.email);
        userEntity.setName(dto.name);
        userEntity.setPassword(dto.password);
        List<Phone> phones = dto.phones.stream()
                .map(phoneDTO -> {
                    Phone phone = new Phone();
                    phone.setNumber(phoneDTO.number);
                    phone.setCitycode(phoneDTO.citycode);
                    phone.setCountrycode(phoneDTO.countrycode);
                    phone.setUser(userEntity);
                    return phone;
                })
                .collect(Collectors.toList());
        userEntity.setPhones(phones);
        // Generar el token JWT
        //final UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getEmail());
        final String token = generateToken(dto);
        userEntity.setToken(token);
        userEntity.setCreatedDate(new Date());
        userEntity.setLastLogin(new Date());
        userEntity.setIsActive(true);
        // Asegúrate de encriptar la contraseña antes de guardarla en la base de datos si es necesario
        userRepository.save(userEntity);

        RegistrationResponseDTO response = new RegistrationResponseDTO();
        response.id = userEntity.getId();
        response.created = userEntity.getCreatedDate();
        response.lastLogin =  userEntity.getLastLogin();
        response.token = token;
        response.isActive = (userEntity.getIsActive()==null)? true : false;

        return new ResponseEntity<>(response,  HttpStatus.CREATED );
    }

    @Override
    public ResponseEntity<Object> login(String token) {
        try {
            User user = this.getUserInfoFromToken(token);
            if (user == null) {
                // El token no es válido, devolver una respuesta de error
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
            LoginResponseDTO response = new LoginResponseDTO();
            response.id = user.getId();
            response.created = user.getCreatedDate();
            response.lastLogin =user.getLastLogin();
            //response.setToken(generateNewToken()); // Genera un nuevo token
            response.isActive = user.getIsActive();
            response.name= user.getName();
            response.email=user.getEmail();
            response.password= user.getPassword(); // Considera si es necesario devolver la contraseña
            /* 
            List<PhoneDTO> phoneDTOs = user.getPhones().stream()
                    .map(phone -> {
                        PhoneDTO phoneDTO = new PhoneDTO();
                        phoneDTO.setNumber(phone.getNumber());
                        phoneDTO.setCitycode(phone.getCitycode());
                        phoneDTO.setCountrycode(phone.getCountrycode());
                        return phoneDTO;
                    })
                    .collect(Collectors.toList());
            response.setPhones(phoneDTOs);
                    */
        return ResponseEntity.ok(response);
        } catch (SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public User updateUser(User user) {
        try {
            // Verificar si el usuario existe
            User existingUser = userRepository.findByEmail(user.getEmail()).orElse();

            // Si el usuario no existe, lanza una excepción personalizada
            if (existingUser == null) {
                throw new CustomException(new ErrorDetail("El usuario no existe.", HttpStatus.NOT_FOUND.value()));
            }

            // Realiza las actualizaciones necesarias en el usuario existente
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            // Actualiza otros campos según tus necesidades

            // Guarda el usuario actualizado en la base de datos
            User updatedUser = userRepository.save(existingUser);

            return updatedUser;
        } catch (CustomException ex) {
            // Captura y maneja excepciones personalizadas
            throw ex;
        } catch (Exception e) {
            // En caso de error interno del servidor, lanza una excepción personalizada
            throw new CustomException(new ErrorDetail("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Override
    public Object checkIfExists(String mail){
        return userRepository.findByEmail(mail);
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            // Buscar al usuario por su dirección de correo electrónico
            User user = userRepository.findByEmail(email);

            // Si el usuario no se encuentra, lanza una excepción personalizada
            if (user == null) {
                throw new CustomException(new ErrorDetail("Usuario no encontrado.", HttpStatus.NOT_FOUND.value()));
            }

            return user;
        } catch (CustomException ex) {
            // Captura y maneja excepciones personalizadas
            throw ex;
        } catch (Exception e) {
            // En caso de error interno del servidor, lanza una excepción personalizada
            throw new CustomException(new ErrorDetail("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    // Función para generar el token JWT
    private String generateToken(UserDTO userDetails) {

        SecretKey secretKey = JWTUtil.generateHS512SecretKey();
        return Jwts.builder()
                .setSubject(userDetails.email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 10 días de duración
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public User getUserInfoFromToken(String token) throws SignatureException {
        try {
            // Valida y decodifica el token JWT
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token.replace("Bearer ", "")); // En caso de que el token incluya "Bearer "

            // Obtiene el email del usuario desde el token
            String email = claimsJws.getBody().getSubject();

            // Busca al usuario en la base de datos por su email
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            // Otra excepción, por ejemplo, token expirado
            return null;
        }
    }

   
}
