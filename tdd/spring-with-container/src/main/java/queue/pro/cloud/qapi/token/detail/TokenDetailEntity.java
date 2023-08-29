package queue.pro.cloud.qapi.token.detail;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import queue.pro.cloud.qapi.common.entity.BaseEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@DynamicUpdate
@Table(name = "token_detail")
public class TokenDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "queue.pro.cloud.qapi.common.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private String id;
    @Version
    @Column(name = "VERSION")
    private Integer version;
    @Column(name = "category_wise_token_seq_no")
    private Integer categoryWiseTokenSeqNo;
    @Column(name = "svc_seq_no_in_a_token")
    private Integer svcSeqNoInAToken;
    @Column(name = "state")
    private Integer state;

    @Column(name = "token_prefix")
    private String tokenPrefix;
    @Column(name = "token_no_int")
    private Integer tokenNoInt;
    @Column(name = "token_no_str")
    private String tokenNoStr;
    @Column(name = "token_issue_date")
    private LocalDateTime  tokenIssueDate;
    @Column(name = "tkis_id")
    private String tkisId;
    @Column(name = "sc_id")
    private String scId;
    @Column(name = "sdcId")
    private String sdcId;
    @Column(name = "sdc_user_id")
    private String sdcUserId;
    @Column(name = "lang_id")
    private Integer langId;
    @Column(name = "token_priority")
    private Integer tokenPriority;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "svc_priority")
    private Integer svcPriority;
    @Column(name = "no_of_try")
    private Integer noOfTry;
    @Column(name = "next_try_time")
    private LocalDateTime  nextTryTime;
    @Column(name = "has_any_followup_svc")
    private Boolean hasAnyFollowupSvc;
    @Column(name = "is_this_a_followup_svc")
    private Boolean isThisAFollowupSvc;
    @Column(name = "tkis_page_navigation")
    private String tkisPageNavigation;
    @Column(name = "cust_identification_type")
    private String custIdentificationType;
    @Column(name = "cust_identification_number")
    private String custIdentificationNumber;
    @Column(name = "customer_mob_number")
    private String customerMobNumber;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "meta_data")
    private String metaData;

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
