// 회원별 일정 엔티티
package dawaga.dawaga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "member_schedule")
public class MemberSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_schedule_pk")
    private Integer memberSchedulePk; // PK

    @ManyToOne
    @JoinColumn(name = "schedule_pk", nullable = false)
    private Schedule schedule; // 일정 FK

    @Column(nullable = false, length = 45)
    private String memberPk; // 사용자 ID

    @Column(nullable = false)
    @ColumnDefault("0")
    private int memberScheduleIsConfirmed; // 참여 여부 (0: 미참여, 1: 참여)

    @Column
    private LocalDateTime memberScheduleConfirmedAt; // 참여 확정 시간

    @Column(nullable = false)
    @ColumnDefault("0")
    private int memberScheduleIsDeleted; // 일정 삭제 여부 (0: 유지, 1: 삭제)

    @Column
    private LocalDateTime memberScheduleDeletedAt; // 일정 삭제된 시간

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime memberScheduleCreatedAt; // 레코드 생성 시간

    public Integer getMemberSchedulePk() {
        return memberSchedulePk;
    }

    public void setMemberSchedulePk(Integer memberSchedulePk) {
        this.memberSchedulePk = memberSchedulePk;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getMemberPk() {
        return memberPk;
    }

    public void setMemberPk(String memberPk) {
        this.memberPk = memberPk;
    }

    public int getMemberScheduleIsConfirmed() {
        return memberScheduleIsConfirmed;
    }

    public void setMemberScheduleIsConfirmed(int memberScheduleIsConfirmed) {
        this.memberScheduleIsConfirmed = memberScheduleIsConfirmed;
    }

    public LocalDateTime getMemberScheduleConfirmedAt() {
        return memberScheduleConfirmedAt;
    }

    public void setMemberScheduleConfirmedAt(LocalDateTime memberScheduleConfirmedAt) {
        this.memberScheduleConfirmedAt = memberScheduleConfirmedAt;
    }

    public int getMemberScheduleIsDeleted() {
        return memberScheduleIsDeleted;
    }

    public void setMemberScheduleIsDeleted(int memberScheduleIsDeleted) {
        this.memberScheduleIsDeleted = memberScheduleIsDeleted;
    }

    public LocalDateTime getMemberScheduleDeletedAt() {
        return memberScheduleDeletedAt;
    }

    public void setMemberScheduleDeletedAt(LocalDateTime memberScheduleDeletedAt) {
        this.memberScheduleDeletedAt = memberScheduleDeletedAt;
    }

    public LocalDateTime getMemberScheduleCreatedAt() {
        return memberScheduleCreatedAt;
    }

    public void setMemberScheduleCreatedAt(LocalDateTime memberScheduleCreatedAt) {
        this.memberScheduleCreatedAt = memberScheduleCreatedAt;
    }
}
