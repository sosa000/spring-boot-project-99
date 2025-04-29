package hexlet.code.app.sevice;

import hexlet.code.app.dto.task.TaskCreateDTO;
import hexlet.code.app.dto.task.TaskDTO;
import hexlet.code.app.dto.task.TaskParamsDTO;
import hexlet.code.app.dto.task.TaskUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.task.TaskMapper;
import hexlet.code.app.model.Task;
import hexlet.code.app.model.TaskLabel;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskSpecification taskSpecification;

    private List<TaskLabel> createTaskLabels(List<Long> labelsIds, Task task) {
        if (labelsIds == null || labelsIds.isEmpty()) {
            return List.of();
        }

        return labelsIds.stream()
                .map(id -> {
                    var label = labelRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Label with id " + id + " not found"));
                    TaskLabel taskLabel = new TaskLabel();
                    taskLabel.setLabel(label);
                    taskLabel.setTask(task);

                    return taskLabel;
                })
                .toList();
    }

    public List<TaskDTO> findAll(TaskParamsDTO params, int page) {
        var spec = taskSpecification.build(params);
        return taskRepository.findAll(spec, PageRequest.of(page - 1, 10))
                .map(taskMapper::map)
                .stream()
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
        } else {
            model.setAssignee(null);
        }

        var status = taskStatusRepository.findBySlug(dto.getStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Task status with slug " + dto.getStatus() + " not found"));
        model.setTaskStatus(status);

        var taskLabels = createTaskLabels(dto.getTaskLabelIds(), model);

        model.setTaskLabels(taskLabels);

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

        setIfPresent(dto.getTaskLabelIds(), labelIds -> {
            var taskLabels = createTaskLabels(labelIds, model);
            model.getTaskLabels().clear();
            model.getTaskLabels().addAll(taskLabels);
        });

        taskRepository.save(model);

        return taskMapper.map(model);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
