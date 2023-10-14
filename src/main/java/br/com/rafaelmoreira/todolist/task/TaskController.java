package br.com.rafaelmoreira.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelmoreira.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request ){ //Http recupera o atributo do request no Filter task
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt()) ){ // Se a data atual for maior do que a data inserida
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A Data de Início/término deve ser maior que a atual.");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt()) ){ // Se a data atual for maior do que a data inserida
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A Data de Início deve ser menor que a de término");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);

    }

    @GetMapping("/")
    public List<TaskModel> list( HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID)idUser);
        return tasks;
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id,HttpServletRequest request ){ 

         var task = this.taskRepository.findById(id).orElse(null);

         if (task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa Inexistente");
         }
        var idUser = request.getAttribute("idUser");

        if (!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário nçao tem permissão para alterar essa tarefa");
        }

        Utils.copyNullPropertyNames(taskModel, task);

        // taskModel.setIdUser((UUID)idUser);
        // taskModel.setId(id);

        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
    
}

   /**
     * Métodos de acesso do HTTP:
     * GET - Buscar Informação
     * POST -  Adicionar um dado/informação
     * PUT - Alterar um dado/info
     * DELETE - Remover um dado
     * PATCH - Alterar somente uma parte da info/dado
     */
