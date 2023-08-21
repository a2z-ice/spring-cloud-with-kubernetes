package queue.pro.cloud.qapi.sdc;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import queue.pro.cloud.qapi.common.entity.BaseEntity;
import queue.pro.cloud.qapi.service.ServiceEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicUpdate
@Table(name= "sdc_info")
public class SdcInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "queue.pro.cloud.qapi.common.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private String id;
    @Version
    @Column(name = "VERSION")
    private Integer version;

//    @ManyToMany(mappedBy = "sdcInfos")
//    private Set<ServiceEntity> services = new HashSet<>();

    @Column(name="name")
    private String name;
    @Column(name="sdu_id")
    private String sduId;
    @Column(name="led_id")
    private String ledId;
    @Column(name="serving_token_no")
    private String servingTokenNo;
    @Column(name="serving_user_login_id")
    private String servingUserLoginId;
    @Column(name="serving_user_login_time")
    private LocalDateTime servingUserLoginTime;
    @Column(name="serving_user_login_state")
    private String servingUserLoginState;
    @Column(name="account_holder_ratio")
    private Integer accountHolderRatio;
    @Column(name="serving_update_on")
    private LocalDateTime serviceUpdateOn;
    @Column(name="start_time")
    private LocalDateTime startTime;
    @Column(name="end_time")
    private LocalDateTime endTime;
    @Column(name="state")
    private Integer state;
    @Column(name = "sc_id")
    private String scId;
    @Column(name = "corporate_id")
    private String corporateId;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;
    @LastModifiedBy
    @Column(nullable = false)
    private String modifiedBy;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modified;
}
