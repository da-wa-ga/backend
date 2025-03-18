package dawaga.dawaga.repository;

import dawaga.dawaga.model.MemberSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 초대된 멤버(Schedule 참가자) 정보를 관리하는 JPA 리포지토리.
 *
 * @author SeryeongK
 */
@Repository
public interface MemberScheduleRepository extends JpaRepository<MemberSchedule, Integer> {
}
