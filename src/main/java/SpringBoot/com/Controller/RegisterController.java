package SpringBoot.com.Controller;

import SpringBoot.com.Entity.User;
import SpringBoot.com.Repository.UserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        ResponseEntity<String> newUser = verifyRegistered(user);
        if (newUser != null) return newUser;
        encrypt(user);
        // Guardar el usuario en la base de datos
        userRepository.save(user);
        return ResponseEntity.ok("Registro exitoso.");
    }

    @Nullable
    private ResponseEntity<String> verifyRegistered(User user) {
        // Verificar si el usuario ya existe en la base de datos
        if (userRepository.existsByuserName(user.getUserName())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya está en uso.");
        }
        return null;
    }

    private void encrypt(User user) {
        // Encriptar la contraseña antes de guardarla en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
