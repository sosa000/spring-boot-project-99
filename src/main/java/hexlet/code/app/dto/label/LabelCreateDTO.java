package hexlet.code.app.dto.label;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelCreateDTO {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 1000)
    @Column(unique = true)
    private String name;
}
