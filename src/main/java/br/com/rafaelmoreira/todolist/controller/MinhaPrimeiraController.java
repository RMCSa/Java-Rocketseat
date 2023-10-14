package br.com.rafaelmoreira.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* 
    Classe designada para ser a primeira camada de acesso onde o ususario envia a requisição para a aplicação; NÃO SERÁ NECESSÁRIA
*/

//@Controller Utiliza quando quer criar uma estrutura com páginas e templates. Mais flexível.- Precisa importar
@RestController //Construção de API. 
@RequestMapping("/primeiraRota") // Definir Rota
// http://localhost:8880 / ------ROTA------  
public class MinhaPrimeiraController {

    /**
     * Métodos de acesso do HTTP:
     * GET - Buscar Informação
     * POST -  Adicionar um dado/informação
     * PUT - Alterar um dado/info
     * DELETE - Remover um dado
     * PATCH - Alterar somente uma parte da info/dado
     */

    //Metodo de uma classe:
    @GetMapping("/primeiroMetodo")
    public String primeiraMensagem() {
        return "Funcionou";
    }
    
}
