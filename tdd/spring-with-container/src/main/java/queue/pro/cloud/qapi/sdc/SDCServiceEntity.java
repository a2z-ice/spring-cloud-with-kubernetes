package queue.pro.cloud.qapi.sdc;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicUpdate
@Table(name= "sdc_service")
public class SDCServiceEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "queue.pro.cloud.qapi.common.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private String id;
    @Version
    @Column(name = "VERSION")
    private Integer version;

    @Column(name="sdc_id")
    private String sdcId;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name="state")
    private Integer state;
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
