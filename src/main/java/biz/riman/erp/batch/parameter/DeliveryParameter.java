package biz.riman.erp.batch.parameter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@JobScope
@Component
public class DeliveryParameter {
    
    // batch 실행되는 시분초
    @Value("#{jobParameters[datetime]}")
    private String datetime;

    // batch명
    @Value("#{jobParameters[job]}")
    private String job;

    
    public LocalDateTime getDatetime() {
        if (Objects.isNull(this.datetime) || this.datetime.isBlank()) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(this.datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public String getStartDate() {
        return this.getDatetime().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getEndDate() {
        return this.getDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
