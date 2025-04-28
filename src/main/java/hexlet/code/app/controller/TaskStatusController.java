package hexlet.code.app.controller;

import hexlet.code.app.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusUpdateDTO;
import hexlet.code.app.sevice.TaskStatusService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class TaskStatusController {

    @Autowired
    private TaskStatusService taskStatusService;

    @GetMapping(path = "/task_statuses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskStatusDTO>> index() {
        var tasksStatuses = taskStatusService.findAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(tasksStatuses.size()))
                .body(tasksStatuses);
    }

    @GetMapping(path = "/task_statuses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskStatusDTO> show(@PathVariable Long id) {
        var taskStatus = taskStatusService.findById(id);

        return ResponseEntity.ok()
                .body(taskStatus);
    }

    @PostMapping(path = "/task_statuses")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskStatusDTO> create(@Valid @RequestBody TaskStatusCreateDTO dto) {
        var taskStatus = taskStatusService.create(dto);

        return ResponseEntity.ok()
                .body(taskStatus);
    }

    @PutMapping(path = "/task_statuses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskStatusDTO> update(@Valid @RequestBody TaskStatusUpdateDTO dto, @PathVariable Long id) {
        var taskStatus = taskStatusService.update(dto, id);

        return ResponseEntity.ok()
                .body(taskStatus);
    }

    @DeleteMapping(path = "/task_statuses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) throws BadRequestException {
        taskStatusService.destroy(id);
    }
}
