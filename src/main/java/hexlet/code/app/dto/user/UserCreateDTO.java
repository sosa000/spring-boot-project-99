package hexlet.code.app.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    @NotNull(message = "Почта отстутствует")
    @NotBlank(message = "Почта не должна быть пустой")
    @Email(message = "Почта должна содержать @")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Имя отсутствует")
    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    @NotNull(message = "Фамилия отсутствует")
    @NotBlank(message = "Фамилия не должна быть пустой")
    private String lastName;

    @NotNull(message = "Пароль отсутсвует")
    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 8, message = "Пароль должен быть больше 8 символов")
    private String password;
}
