package dawaga.dawaga.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    private String id;

    @NotBlank
    private String password;

    public @NotBlank String getId() {
        return id;
    }

    public @NotBlank String getPassword() {
        return password;
    }
}
