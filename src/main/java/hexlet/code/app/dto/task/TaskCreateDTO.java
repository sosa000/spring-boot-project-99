package hexlet.code.app.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskCreateDTO {
    private Long index;
    private Long assignee_id;
    private List<Long> taskLabelIds;

    @NotNull
    @NotBlank
    @Size(min = 1)
    private String title;

    private String content;

    @NotNull
    private String status;
}
