package queue.pro.cloud.qapi.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;


public class CustomDateZoneAuditAware implements AuditorAware<LocalDateTime> {
    @Override
    public Optional<LocalDateTime> getCurrentAuditor() {
        return Optional.of(LocalDateTime.now());
    }
}
