package queue.pro.cloud.qapi.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "dateZoneAuditWare")
public class AuditConfiguration {
    @Bean
    public AuditorAware<Date> dateZoneAuditWare(){
        return new CustomDateZoneAuditAware();
    }
}
