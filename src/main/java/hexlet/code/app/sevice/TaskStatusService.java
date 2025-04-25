package hexlet.code.app.sevice;

import hexlet.code.app.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.taskstatus.TaskStatusMapper;
import hexlet.code.app.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusMapper taskStatusMapper;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public List<TaskStatusDTO> findAll() {
        return taskStatusRepository.findAll()
                .stream()
                .map(taskStatusMapper::map)
                .toList();
    }

    public TaskStatusDTO findById(Long id) {
        var model = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id " + id + " not found"));
        return taskStatusMapper.map(model);
    }

    public TaskStatusDTO create(TaskStatusCreateDTO dto) {
        var model = taskStatusMapper.map(dto);
        taskStatusRepository.save(model);
        return taskStatusMapper.map(model);
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO dto, Long id) {
        var model = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id " + id + " not found"));
        taskStatusMapper.update(dto, model);
        taskStatusRepository.save(model);

        return taskStatusMapper.map(model);
    }

    public void destroy(Long id) {
        taskStatusRepository.deleteById(id);
    }
}
