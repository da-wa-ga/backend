package dawaga.dawaga.service;

import dawaga.dawaga.dto.schedule.CreateInvitationRequest;
import dawaga.dawaga.model.MemberSchedule;
import dawaga.dawaga.model.Schedule;
import dawaga.dawaga.model.User;
import dawaga.dawaga.repository.MemberScheduleRepository;
import dawaga.dawaga.repository.ScheduleRepository;
import dawaga.dawaga.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * 약속 관련 비즈니스 로직을 처리하는 서비스 클래스.
 *
 * @author SeryeongK
 */
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final MemberScheduleRepository memberScheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository,
                           MemberScheduleRepository memberScheduleRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.memberScheduleRepository = memberScheduleRepository;
    }

    /**
     * 새로운 약속을 생성하고 초대 멤버를 추가하는 메서드.
     *
     * @param request 약속 생성 요청 정보 (제목, 날짜, 장소, 주최자 ID, 초대 멤버 리스트)
     * @return 생성된 약속 객체
     *
     * @throws RuntimeException 주최자가 존재하지 않는 경우 "주최자를 찾을 수 없습니다."
     */
    @Transactional
    public Schedule createSchedule(CreateInvitationRequest request) {
        // 날짜 변환 (yyyy-MM-ddTHH:mm:ss 형식)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime scheduleDate = LocalDateTime.parse(request.getDate(), formatter);

        // 새로운 약속 생성
        Schedule schedule = new Schedule();
        schedule.setScheduleTitle(request.getTitle());
        schedule.setScheduleDatetime(scheduleDate);
        schedule.setScheduleAddress(request.getLocation());

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        schedule.setScheduleHostPk(userId);
        schedule.setScheduleIsCanceled(0); // 기본값: 취소되지 않음
        schedule.setScheduleCreatedAt(LocalDateTime.now());

        // 약속 저장
        schedule = scheduleRepository.save(schedule);

        // 초대된 멤버 추가
        MemberSchedule memberSchedule = new MemberSchedule();
        memberSchedule.setMemberPk(userId);
        memberSchedule.setSchedule(schedule);
        memberSchedule.setMemberScheduleIsConfirmed(0); // 기본값: 응답 대기중
        memberSchedule.setMemberScheduleIsDeleted(0); // 기본값: 미삭제
        memberSchedule.setMemberScheduleCreatedAt(LocalDateTime.now());

        memberScheduleRepository.save(memberSchedule);

        return schedule;
    }
}