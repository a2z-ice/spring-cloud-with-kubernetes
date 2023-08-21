package queue.pro.cloud.qapi.sdc.bean;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SDCTokenBean {
    private String id;
    private String tokenPrefix;
    private Integer tokenNoInt;
    private String tokenNoStr;
    private LocalDateTime tokenIssueDate;
    private String tkisId;
    private String scId;
    private String sdcId;
    private String sdcUserId;
    private Integer langId;
    private Integer tokenPriority;
    private String serviceId;
    private String corporateId;
}
