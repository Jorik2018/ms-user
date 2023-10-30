package com.nttdata.user.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.nttdata.user.exception.UserException;
import com.nttdata.user.model.entity.Phone;
import com.nttdata.user.model.entity.User;
import com.nttdata.user.repository.*;
import com.nttdata.user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!user.getEmail().matches(emailRegex)) {
            throw new UserException("El formato del correo es incorrecto");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("El correo ya registrado");
        }

        // Validación de formato del password (Una Mayúscula, letras minúsculas y dos
        // números)
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z].*[a-z])(?=.*\\d.*\\d).+$";
        if (!user.getPassword().matches(passwordRegex)) {
            throw new UserException("El formato del password es incorrecto");
        }
        LocalDateTime now = LocalDateTime.now();
        user.setCreated(now);
        user.setModified(now);
        user.setToken(jwtUtil.generateToken(user));
        user.setLastLogin(now);
        user.setActive(true);
        user.setId(UUID.randomUUID().toString());
        List<Phone> phones = user.getPhones();
        user.setPhones(new ArrayList<>());
        System.out.println("insert "+user.getId());
        User savedUser = userRepository.save(user);
        if (phones != null) {
            for (Phone phone : phones) {
                Optional<Phone> existingPhone = phoneRepository.findById(phone.getNumber());
                if (existingPhone.isPresent()) {
                    throw new UserException("El teléfono ya está registrado");
                }
                phone.setUser(savedUser);
                phoneRepository.save(phone);
            }
        }
        
        return savedUser;
    }
}
