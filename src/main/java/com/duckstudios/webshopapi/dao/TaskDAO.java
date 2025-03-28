package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.TaskDTO;
import com.duckstudios.webshopapi.models.Category;
import com.duckstudios.webshopapi.models.Task;
import com.duckstudios.webshopapi.services.EntityValidator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskDAO {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final EntityValidator entityValidator;

    public TaskDAO(TaskRepository taskRepository, CategoryRepository categoryRepository, EntityValidator entityValidator) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.entityValidator = entityValidator;
    }

    public List<Task> getAllTasks(){
        //noinspection UnnecessaryLocalVariable
        List<Task> tasks = this.taskRepository.findAll();
        // Opgesplitst in 2 lines, voor leesbaarheid
        return tasks;
    }

    public Optional<Task> getTask(long id) {
        entityValidator.checkIfIdExists(id, taskRepository, "Task");
        //noinspection UnnecessaryLocalVariable
        Optional<Task> task = this.taskRepository.findById(id);
        // Opgesplitst in 2 lines, voor leesbaarheid
        return task;
    }

//    public void createTask(TaskDTO taskDTO) {
//        // category ophalen a.d.h.v.het gegeven id in taskDTO.
//        // check: bestaat category met specifiek id wel?
//        // if category with specific id is existent -> make new task, with specific category.
//        // else category with specific id is non-existent, throw error.
//        // schrijf taak weg naar db.
//        Optional<Category> optionalCategory = this.categoryRepository.findById(taskDTO.categoryId);
//        if (optionalCategory.isPresent()) {
//            Category category = optionalCategory.get();
//            Task task = new Task(taskDTO.name, taskDTO.description, category);
//            this.taskRepository.save(task);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Category with that id, non-existent"
//
//            );
//        }
//    }


    public void createTask(TaskDTO taskDTO) {
        Category category = entityValidator.checkIfIdExists(taskDTO.categoryId, categoryRepository, "Category");
        Task task = new Task(taskDTO.name, taskDTO.description, category);
        this.taskRepository.save(task);
    }

//    public void updateTaskByID(long id, TaskDTO taskDTO) {
//        // task ophalen a.d.h.v. het gegeven id
//        // Check: bestaat task met specifieke id wel?
//        // update tasks data (name, desc.)
//        // save task data to db.
//        Optional<Task> optionalTask = this.taskRepository.findById(id);
//        if (optionalTask.isPresent()) {
//            Task updatedTask = optionalTask.get();
//            updatedTask.setName(taskDTO.name);
//            updatedTask.setDescription(taskDTO.description);
//            this.taskRepository.save(updatedTask);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Task met dat id bestaat niet!"
//            );
//        }
//    }

    public void updateTaskByID(long id, TaskDTO taskDTO) {
        Task task = entityValidator.checkIfIdExists(id, taskRepository, "Task");
        task.setName(taskDTO.name);
        task.setDescription(taskDTO.description);
        this.taskRepository.save(task);
    }

//    public void checkTask(long id) {
//        // task ophalen a.d.h.v. het gegeven id
//        // Check: bestaat task met specifieke id wel?
//        // update task bool isFinished to true
//        // save task bool isFinished to db
//        Optional<Task> optionalTask = this.taskRepository.findById(id);
//        if (optionalTask.isPresent()) {
//            Task task = optionalTask.get();
//            task.setFinished(true);
//            this.taskRepository.save(task);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Task met dat id bestaat niet!"
//            );
//        }
//    }

    public void checkTask(long id) {
        Task task = entityValidator.checkIfIdExists(id, taskRepository, "Task");
        task.setFinished(true);
        this.taskRepository.save(task);
    }

//    public void uncheckTask(long id) {
//        // task ophalen a.d.h.v. het gegeven id
//        // Check: bestaat task met specifieke id wel?
//        // update task bool isFinished to false
//        // save task bool isFinished to db
//        Optional<Task> optionalTask = this.taskRepository.findById(id);
//        if (optionalTask.isPresent()) {
//            Task task = optionalTask.get();
//            task.setFinished(false);
//            this.taskRepository.save(task);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Task met dat id bestaat niet!"
//            );
//        }
//    }

    public void uncheckTask(long id) {
        Task task = entityValidator.checkIfIdExists(id, taskRepository, "Task");
        task.setFinished(false);
        this.taskRepository.save(task);
    }

//    public void deleteTask(long id) {
//        // task ophalen a.d.h.v. het gegeven id
//        // Check: bestaat task met specifieke id wel?
//        // update task bool isFinished to false
//        // save task bool isFinished to db
//        Optional<Task> optionalTask = this.taskRepository.findById(id);
//        if (optionalTask.isPresent()) {
//            Task task = optionalTask.get();
//            this.taskRepository.delete(task);
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Task met dat id bestaat niet!"
//            );
//        }
//    }

    public void deleteTask(long id) {
        Task task = entityValidator.checkIfIdExists(id, taskRepository, "Task");
        this.taskRepository.delete(task);
    }

//    private Object checkIfIdExists(long id, JpaRepository<?, ?> repository, String entityName) {
//        return repository.findById(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " met dat id bestaat niet!"));
//    }

//    private <T, ID> T checkIfIdExists(ID id, JpaRepository<T, ID> repository, String entityName) {
//        return repository.findById(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " met dat id bestaat niet!"));
//    }

    //uitdaging-1: maak uncheckTask.
    //uitdaging-3: maak deleteTask.
    //uitdaging-2: maak DRY code
}
