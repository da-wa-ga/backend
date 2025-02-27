// 데이터베이스에 접근하여 User 엔티티에 대한 작업을 수행
package dawaga.dawaga.respository;

import dawaga.dawaga.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * User 엔티티에 대한 데이터베이스 작업을 수행
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserId(String userId);
    boolean existsByUserNickname(String userNickname);
    Optional<User> findByUserId(String userId);
}
