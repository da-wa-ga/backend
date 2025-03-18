package dawaga.dawaga.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 약속 초대 요청을 처리하는 DTO 클래스.
 *
 * @author SeryeongK
 */
@Getter
@Setter
public class CreateInvitationRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "날짜는 필수입니다.")
    private String date;

    @NotBlank(message = "장소는 필수입니다.")
    private String location;

    public @NotBlank(message = "제목은 필수입니다.") String getTitle() {
        return title;
    }

    public @NotBlank(message = "날짜는 필수입니다.") String getDate() {
        return date;
    }

    public @NotBlank(message = "장소는 필수입니다.") String getLocation() {
        return location;
    }
}
