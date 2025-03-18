package dawaga.dawaga.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeNicknameRequest {

    @NotBlank(message = "새로운 닉네임을 입력하세요.")
    private String newNickname;

    public ChangeNicknameRequest() {}

    public ChangeNicknameRequest(String newNickname) {
        this.newNickname = newNickname;
    }

    public @NotBlank(message = "새로운 닉네임을 입력하세요.") String getNewNickname() {
        return newNickname;
    }
}
