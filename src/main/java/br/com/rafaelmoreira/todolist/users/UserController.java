package br.com.rafaelmoreira.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificadores:
 * public
 * private
 * protected
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired // Gerencia o ciclo de vida do repositório
    private IUserRepository userRepository; // Criação de um repositório
    /*
     * RequestBody. Essas info estarão no body da aplicação
     */

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            System.out.println("Usuário já Existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }
        var passwordHashred = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        System.out.println("\nNovo Usuário: " + userModel.toString());
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }

}
