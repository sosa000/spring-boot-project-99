package hexlet.code.app.sevice;

import hexlet.code.app.dto.task.TaskCreateDTO;
import hexlet.code.app.dto.task.TaskDTO;
import hexlet.code.app.dto.task.TaskUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.task.TaskMapper;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static hexlet.code.app.util.JsonNullableUtils.setIfPresent;

@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public List<TaskDTO> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskDTO findById(Long id) {
        var model = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        return taskMapper.map(model);
    }

    public TaskDTO create(TaskCreateDTO dto) {
        var model = taskMapper.map(dto);

        var userId = dto.getAssignee_id();

        if (userId != null) {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
            model.setAssignee(user);
        }

        var status = taskStatusRepository.findBySlug(dto.getStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Task status with slug " + dto.getStatus() + " not found"));

        model.setTaskStatus(status);

        taskRepository.save(model);
        return taskMapper.map(model);
    }

    public TaskDTO update(TaskUpdateDTO dto, Long id) {
        var model = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        taskMapper.update(dto, model);

        setIfPresent(dto.getAssignee_id(), userId -> {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
            model.setAssignee(user);
        });

        setIfPresent(dto.getStatus(), statusSlug -> {
            var taskStatus = taskStatusRepository.findBySlug(statusSlug)
                    .orElseThrow(() -> new ResourceNotFoundException("Task status with slug " + statusSlug + " not found"));
            model.setTaskStatus(taskStatus);
        });

        taskRepository.save(model);

        return taskMapper.map(model);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
