package hexlet.code.app.mapper.taskstatus;

import hexlet.code.app.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusUpdateDTO;
import hexlet.code.app.mapper.JsonNullableMapper;
import hexlet.code.app.model.TaskStatus;
import org.mapstruct.*;

@Mapper(
        uses = { JsonNullableMapper.class },
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class TaskStatusMapper {
    public abstract TaskStatus map(TaskStatusCreateDTO dto);
    public abstract TaskStatusDTO map(TaskStatus model);
    public abstract void update(TaskStatusUpdateDTO dto, @MappingTarget TaskStatus model);
}
