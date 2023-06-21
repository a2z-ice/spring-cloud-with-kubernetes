package queue.pro.cloud.qapi.token;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "token")
public class Token {
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
    @Column(name = "token_prefix")
    private String tokenPrefix;
    @Column(name = "token_no_int")
    private Integer tokenNoInt;
    @Column(name = "token_no_str")
    private String tokenNoStr;
    @Column(name = "token_issue_date")
    private Date tokenIssueDate;
    @Column(name = "tkis_id")
    private String tkisId;
    @Column(name = "sc_id")
    private String scId;
    @Column(name = "lang_id")
    private String langId;
    @Column(name = "token_priority")
    private Integer tokenPriority;
    @Column(name = "service_id")
    private String serviceId;
    @Column(name = "svc_priority")
    private Integer svcPriority;
    @Column(name = "no_of_try")
    private Integer noOfTry;
    @Column(name = "next_try_time")
    private Date nextTryTime;
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
