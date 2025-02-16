// 회원 엔티티
package dawaga.dawaga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pk")
    private Integer userPk;

    @Column(name = "id", nullable = false, unique = true, length = 45)
    private String userId;

    @Column(name = "name", length = 45)
    private String userName;

    @Column(name = "nickname", length = 45)
    private String userNickname;

    @Column(name = "password", length = 45)
    private String userPassword;

    @Column(name = "address", length = 255)
    private String userAddress;

    @Column(name = "profile_pk", length = 45)
    private String userProfilePk;

    @Column(name = "withdraw_yn", length = 45, columnDefinition = "VARCHAR(45) DEFAULT '0'")
    private String userWithdrawYn;

    @Column(name = "withdraw_date", length = 45)
    private String userWithdrawDate;

    @Column(name = "updated_at", length = 45)
    private String userUpdatedAt;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime userCreatedAt;
}
