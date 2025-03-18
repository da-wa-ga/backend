package dawaga.dawaga.controller;

import dawaga.dawaga.dto.schedule.CreateInvitationRequest;
import dawaga.dawaga.model.Schedule;
import dawaga.dawaga.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 약속(Schedule) 관련 요청을 처리하는 컨트롤러.
 *
 * <p>약속 생성 및 초대장 관련 API를 제공한다.</p>
 *
 * @author SeryeongK
 */
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 초대장을 생성하는 API.
     *
     * @param createInvitationRequest 초대장 생성 요청 정보 (제목, 날짜, 장소, 주최자, 초대할 멤버 리스트)
     * @return 초대장이 성공적으로 생성되었을 경우 메시지 반환
     *
     * @throws RuntimeException 주최자가 존재하지 않거나 다른 문제가 발생할 경우 예외 발생
     */
    @Operation(summary = "초대장 생성")
    @PostMapping("/create-invitation")
    public ResponseEntity<?> createInvitation(@Valid @RequestBody CreateInvitationRequest createInvitationRequest) {
        try {
            Schedule schedule = scheduleService.createSchedule(createInvitationRequest);
            return ResponseEntity.ok("초대장이 성공적으로 생성되었습니다. ID: " + schedule.getSchedulePk());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("초대장 생성 실패: " + e.getMessage());
        }
    }
}
