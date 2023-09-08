package com.globalLogic.usermircroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.globalLogic.usermircroservice.model.User;
import com.globalLogic.usermircroservice.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos por su email
        User user = userRepository.findByEmail(email);

        //if (user == null) {
            //throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        //}

        // Devolver un UserDetails que Spring Security pueda usar para la autenticación
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER") // Puedes asignar roles de usuario según tu lógica
                .build();
    }
}
