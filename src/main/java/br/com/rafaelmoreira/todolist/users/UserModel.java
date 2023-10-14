package br.com.rafaelmoreira.todolist.users;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data 
/*Define os métodos Get e Set automaticamente. Mude na dependência do projeto para que possa ser usado.*/ 
@Entity(name = "tb_users") //Criação de tabela no bd
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    
    @Column(unique = true) // --> Restringir o atributo para unico
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

/* 
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
*/
    // public UserModel(String name, String username, String password) throws Exception{
    //     setName(name);
    //     setUsername(username);
    //     setPassword(password);
    // }


    @Override
    public String toString(){
        return "\nName: "+ name + "\nUsername: " + username + "\nPassword: " + password;
    }
    
    
}
