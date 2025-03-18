package dawaga.dawaga.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeProfileRequest {

    @NotBlank(message = "변경하고자 하는 이미지의 url을 입력하세요.")
    private String oldProfileUrl;

    public ChangeProfileRequest() {}

    public ChangeProfileRequest(String newNickname) {
        this.oldProfileUrl = newNickname;
    }

    public @NotBlank(message = "변경하고자 하는 이미지의 url을 입력하세요.") String getNewNickname() {
        return oldProfileUrl;
    }
}
