package dawaga.dawaga.controller;

import dawaga.dawaga.dto.user.ChangeAddressRequest;
import dawaga.dawaga.dto.user.ChangeNicknameRequest;
import dawaga.dawaga.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "닉네임 변경")
    @PostMapping("/change-nickname")
    public ResponseEntity<?> changeNickname(@Valid @RequestBody ChangeNicknameRequest changeNicknameRequest) {
        try {
            userService.changeNickname(changeNicknameRequest);
            return ResponseEntity.ok("닉네임이 성공적으로 변경되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "주소 변경")
    @PostMapping("/change-address")
    public ResponseEntity<?> changeAddress (@Valid @RequestBody ChangeAddressRequest changeAddressRequest) {
        try {
            userService.changeAddress(changeAddressRequest);
            return ResponseEntity.ok("주소가 성공적으로 변경되었습니다.");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "프로필 변경")
    @PostMapping(value = "/change-profile", consumes = "multipart/form-data")
    public ResponseEntity<String> changeProfile(
            @Parameter(
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary"))
            )
            @RequestParam("file") MultipartFile file) {
        String newProfileUrl = userService.changeProfileImage(file);
        return ResponseEntity.ok(newProfileUrl);
    }
}
