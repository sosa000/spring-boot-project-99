package hexlet.code.app.controller;

import hexlet.code.app.dto.task.TaskCreateDTO;
import hexlet.code.app.dto.task.TaskDTO;
import hexlet.code.app.dto.task.TaskUpdateDTO;
import hexlet.code.app.sevice.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping(path = "/tasks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDTO>> index() {
        var tasks = taskService.findAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(tasks.size()))
                .body(tasks);
    }

    @GetMapping(path = "/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskDTO> show(@PathVariable Long id) {
        var task = taskService.findById(id);

        return ResponseEntity.ok()
                .body(task);
    }

    @PostMapping(path = "/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskCreateDTO dto) {
        var task = taskService.create(dto);

        return ResponseEntity.ok()
                .body(task);
    }

    @PutMapping(path = "/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskDTO> update(@Valid @RequestBody TaskUpdateDTO dto, @PathVariable Long id) {
        var task = taskService.update(dto, id);

        return ResponseEntity.ok()
                .body(task);
    }

    @DeleteMapping(path = "/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        taskService.delete(id);
    }
}
