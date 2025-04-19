package com.Julienshop.webshopapi.controllers;

import com.Julienshop.webshopapi.dto.TaskDTO;
import com.Julienshop.webshopapi.dao.TaskDAO;
import com.Julienshop.webshopapi.models.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/tasks")
public class TaskController {

    private final TaskDAO taskDAO;

    public TaskController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask() {
//        Task myTask = new Task("Videos maken", "Springboot videos maken");
        List<Task> tasks = this.taskDAO.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTask(@PathVariable long id) {
        Optional<Task> task = this.taskDAO.getTask(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO ){
        // De gebruiker moet een set aan data krijgen. → naam van task en description van task.
        this.taskDAO.createTask(taskDTO);
        // De DAO vragen om de task te gaan maken en weg te schrijven naar de database. (in de TaskDAO).
        // Debug message if writing to db has gone good.
        return ResponseEntity.ok("Het is gelukt!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable long id, @RequestBody TaskDTO taskDTO) {
        this.taskDAO.updateTaskByID(id, taskDTO);
        return ResponseEntity.ok("Updated task with id " + id);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<String> checkTask(@PathVariable long id) {
        this.taskDAO.checkTask(id);
        return ResponseEntity.ok("Finished task with id " + id);
    }

    @PutMapping("/uncheck/{id}")
    public ResponseEntity<String> uncheckTask(@PathVariable long id) {
        this.taskDAO.uncheckTask(id);
        return ResponseEntity.ok("Unfinished task with id " + id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteTask(@PathVariable long id) {
        this.taskDAO.deleteTask(id);
        return ResponseEntity.ok("Deleted task with id " + id);
    }
}
