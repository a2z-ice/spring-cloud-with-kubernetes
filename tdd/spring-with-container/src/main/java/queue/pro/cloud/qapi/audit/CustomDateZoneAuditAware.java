package queue.pro.cloud.qapi.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;


public class CustomDateZoneAuditAware implements AuditorAware<Date> {
    @Override
    public Optional<Date> getCurrentAuditor() {
        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.systemDefault());
        Calendar calendar = Calendar.getInstance(timeZone);
        return Optional.of(calendar.getTime());
    }
}
