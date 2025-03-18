package dawaga.dawaga.repository;

import dawaga.dawaga.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 약속(Schedule) 데이터를 관리
 *
 * @author SeryeongK
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
