package queue.pro.cloud.qapi.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
//@EnableJpaAuditing(auditorAwareRef = "dateZoneAuditWare")
@EnableJpaAuditing
public class AuditConfiguration {
//    @Bean
//    public AuditorAware<LocalDateTime> dateZoneAuditWare(){
//        return new CustomDateZoneAuditAware();
//    }
}
