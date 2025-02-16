// 데이터베이스에 접근하여 User 엔티티에 대한 작업을 수행
package dawaga.dawaga.respository;

import dawaga.dawaga.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * User 엔티티에 대한 데이터베이스 작업을 수행
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    // 로그인 아이디(userId) 중복 여부를 확인하기 위한 메서드
    boolean existsByUserId(String userId);

    // 닉네임 중복 여부를 확인하기 위한 메서드
    boolean existsByUserNickname(String userNickname);

    // 로그인 아이디(userId)로 회원 정보를 조회하는 메서드 (옵션)
    Optional<User> findByUserId(String userId);
}
