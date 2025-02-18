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

    @Column(name = "id", nullable = false, unique = true, length = 255)
    private String userId;

    @Column(name = "name", length = 255)
    private String userName;

    @Column(name = "nickname", length = 255)
    private String userNickname;

    @Column(name = "password", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "address", length = 255)
    private String userAddress;

    @Column(name = "profile_pk")
    private Integer userProfilePk;

    @Column(name = "withdraw_yn", columnDefinition = "TINYINT DEFAULT 0")
    private Integer userWithdrawYn;

    @Column(name = "withdraw_date")
    private LocalDateTime userWithdrawDate;

    @Column(name = "updated_at")
    private LocalDateTime userUpdatedAt;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime userCreatedAt;

    public Integer getUserPk() {
        return userPk;
    }

    public void setUserPk(Integer userPk) {
        this.userPk = userPk;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Integer getUserProfilePk() {
        return userProfilePk;
    }

    public void setUserProfilePk(Integer userProfilePk) {
        this.userProfilePk = userProfilePk;
    }

    public Integer getUserWithdrawYn() {
        return userWithdrawYn;
    }

    public void setUserWithdrawYn(Integer userWithdrawYn) {
        this.userWithdrawYn = userWithdrawYn;
    }

    public LocalDateTime getUserWithdrawDate() {
        return userWithdrawDate;
    }

    public void setUserWithdrawDate(LocalDateTime userWithdrawDate) {
        this.userWithdrawDate = userWithdrawDate;
    }

    public LocalDateTime getUserUpdatedAt() {
        return userUpdatedAt;
    }

    public void setUserUpdatedAt(LocalDateTime userUpdatedAt) {
        this.userUpdatedAt = userUpdatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.userCreatedAt = LocalDateTime.now();
    }

    public LocalDateTime getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(LocalDateTime userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }
}
