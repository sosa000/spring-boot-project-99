package hexlet.code.app.dto.task;

import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private Long index;
    private Date createdAt;
    private Long assignee_id;
    private String title;
    private String content;
    private String status;
}
