package SpringBoot.com.Controller;

import SpringBoot.com.Entity.User;
import SpringBoot.com.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody User userRequest) {
        userRepository.save(userRequest); // Guardar el usuario en la base de datos
        return "Usuario creado exitosamente";
    }

    // Endpoint para actualizar un usuario por su ID
    @PutMapping("/updateUser/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User userRequest) {
        Optional<User> optionalUser = userRepository.findById(id); // Buscar el usuario por su ID
        optionalUser.ifPresent(user -> {
            user.setName(userRequest.getName()); // Actualizar el nombre del usuario
            user.setAge(userRequest.getAge()); // Actualizar la edad del usuario
            userRepository.save(user); // Guardar los cambios en la base de datos
        });
        return "Usuario Actualizado exitosamente";
    }

    // Endpoint para eliminar un usuario por su ID
    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id); // Buscar el usuario por su ID
        optionalUser.ifPresent(user -> {
            userRepository.delete(user); // Eliminar el usuario de la base de datos
        });
        return "Usuario Eliminado exitosamente";
    }
}
