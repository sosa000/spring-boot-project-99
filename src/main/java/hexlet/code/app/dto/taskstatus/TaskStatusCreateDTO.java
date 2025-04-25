package hexlet.code.app.dto.taskstatus;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusCreateDTO {
    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String name;

    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String slug;
}
