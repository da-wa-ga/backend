package dawaga.dawaga.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String id;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자리 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    public @NotBlank(message = "아이디는 필수입니다.") String getId() {
        return id;
    }

    public @NotBlank(message = "이름은 필수입니다.") String getName() {
        return name;
    }

    public @NotBlank(message = "닉네임은 필수입니다.") String getNickname() {
        return nickname;
    }

    public @NotBlank(message = "비밀번호는 필수입니다.") @Size(min = 6, message = "비밀번호는 최소 6자리 이상이어야 합니다.") String getPassword() {
        return password;
    }

    public @NotBlank(message = "주소는 필수입니다.") String getAddress() {
        return address;
    }
}
