package dawaga.dawaga.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeAddressRequest {

    @NotBlank(message = "새로운 주소를 입력하세요.")
    private String newAddress;

    public ChangeAddressRequest() {}

    public ChangeAddressRequest(String newNickname) {
        this.newAddress = newNickname;
    }

    public @NotBlank(message = "새로운 주소를 입력하세요.") String getNewAddress() {
        return newAddress;
    }
}
