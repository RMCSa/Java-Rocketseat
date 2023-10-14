package br.com.rafaelmoreira.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelmoreira.todolist.users.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //Todo classe que o Spring gerencia necessita de um Component
public class FilterTaskAuth extends OncePerRequestFilter { //Filtrar as info para o http

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var servletPath = request.getServletPath();

                if (servletPath.startsWith("/tasks/")){

                    // Pegar a autenticação (user e senha)
                    var authorization = request.getHeader("Authorization"); // retorna "Basic Uk1DU2E6MTIzNDU=" e deve ser decodificado
                    var authEncoded = authorization.substring("Basic".length()).trim(); //extrair parte de um texto recebendo (int,int) ou apenas (int). Ele irá calcular os caracteres da palavra e retirar ela de authorization utilizando o trim();

                    byte[] authDecode = Base64.getDecoder().decode(authEncoded); //Decodificar informação

                    var authString = new String(authDecode);

                    String[] credentials = authString.split(":"); //Dividir em duas partes a partit de onde vc disse
                    //["usuer", "password"]
                    String username = credentials[0];
                    String password = credentials[1];

                    System.out.println("Authorization");
                    System.out.println(username);
                    System.out.println(password);

                    //validar Usuario
                    var user = this.userRepository.findByUsername(username);
                    if (user == null){
                        response.sendError(401, "Usuário Inexistente");
                    }else{
                        //Validar Senha
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(),user.getPassword());//Comparar a senha nova com a existente;
                        if (passwordVerify.verified){
                            //Segue viagem
                            request.setAttribute("idUser", user.getId());
                            filterChain.doFilter(request, response);//request -> tudo o que vem; Respone -> oq vai;
                        }
                        else{
                            response.sendError(401);
                        }
                    }
                } else{
                    filterChain.doFilter(request, response);
                }
    }

    
}
