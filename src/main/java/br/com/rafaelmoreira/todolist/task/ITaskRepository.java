package br.com.rafaelmoreira.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);
    // TaskModel findByIdAndUser(UUID id,UUID idUser);
    
}
