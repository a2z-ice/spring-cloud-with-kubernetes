package queue.pro.cloud.qapi.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "queue.pro.cloud.qapi.common.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private String id;

    @ManyToMany
    @JoinTable(
            name = "sdc_service_info",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "sdc_id")
    )
    private Set<SdcInfoEntity> sdcInfos = new HashSet<>();


    @NotBlank(message = "service.name must be present")
    @Column(nullable = false, updatable = false)
    private String name;
    @Column(nullable = false, updatable = false)
    private String prefix;
    @Column(nullable = false, updatable = false)
    private Integer priority;
    @Version
    @Column(name = "VERSION")
    private Integer version;
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
