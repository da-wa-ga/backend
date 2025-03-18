// 스케줄 엔티티
package dawaga.dawaga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schedulePk; // 일정의 PK

    @Column(nullable = false, length = 255)
    private String scheduleTitle; // 일정 제목

    @Column(nullable = false)
    private LocalDateTime scheduleDatetime; // 일정 날짜 및 시간

    @Column(nullable = false, length = 255)
    private String scheduleAddress; // 일정 주소

    @Column(nullable = false, length = 45)
    private String scheduleHostPk; // 주최자 PK

    @Column(nullable = false)
    @ColumnDefault("0") // 기본값 0
    private int scheduleIsCanceled; // 취소 여부 (0: 진행, 1: 취소)

    @Column
    private LocalDateTime scheduleCanceledAt; // 일정 취소 시간

    @Column(nullable = false, updatable = false)
    @CreationTimestamp // 일정 생성 시간 자동 기록
    private LocalDateTime scheduleCreatedAt;

    @Column
    @UpdateTimestamp // 일정이 수정될 때 자동 업데이트
    private LocalDateTime scheduleUpdatedAt;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberSchedule> memberSchedules; // 일정에 참여한 멤버들

    public Integer getSchedulePk() {
        return schedulePk;
    }

    public void setSchedulePk(Integer schedulePk) {
        this.schedulePk = schedulePk;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public LocalDateTime getScheduleDatetime() {
        return scheduleDatetime;
    }

    public void setScheduleDatetime(LocalDateTime scheduleDatetime) {
        this.scheduleDatetime = scheduleDatetime;
    }

    public String getScheduleAddress() {
        return scheduleAddress;
    }

    public void setScheduleAddress(String scheduleAddress) {
        this.scheduleAddress = scheduleAddress;
    }

    public String getScheduleHostPk() {
        return scheduleHostPk;
    }

    public void setScheduleHostPk(String scheduleHostPk) {
        this.scheduleHostPk = scheduleHostPk;
    }

    public int getScheduleIsCanceled() {
        return scheduleIsCanceled;
    }

    public void setScheduleIsCanceled(int scheduleIsCanceled) {
        this.scheduleIsCanceled = scheduleIsCanceled;
    }

    public LocalDateTime getScheduleCanceledAt() {
        return scheduleCanceledAt;
    }

    public void setScheduleCanceledAt(LocalDateTime scheduleCanceledAt) {
        this.scheduleCanceledAt = scheduleCanceledAt;
    }

    public LocalDateTime getScheduleCreatedAt() {
        return scheduleCreatedAt;
    }

    public void setScheduleCreatedAt(LocalDateTime scheduleCreatedAt) {
        this.scheduleCreatedAt = scheduleCreatedAt;
    }

    public LocalDateTime getScheduleUpdatedAt() {
        return scheduleUpdatedAt;
    }

    public void setScheduleUpdatedAt(LocalDateTime scheduleUpdatedAt) {
        this.scheduleUpdatedAt = scheduleUpdatedAt;
    }

    public List<MemberSchedule> getMemberSchedules() {
        return memberSchedules;
    }

    public void setMemberSchedules(List<MemberSchedule> memberSchedules) {
        this.memberSchedules = memberSchedules;
    }
}
