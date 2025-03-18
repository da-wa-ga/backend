package dawaga.dawaga.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawRequest {

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;  // 탈퇴 확인을 위한 비밀번호

    public WithdrawRequest(String password) {
        this.password = password;
    }

    public @NotBlank(message = "비밀번호를 입력하세요.") String getPassword() {
        return password;
    }
}
