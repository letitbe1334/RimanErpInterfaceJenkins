package biz.riman.erp.batch.parameter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@JobScope
@Component
public class BusinessPartnerParameter {
    
    // batch 실행되는 시분초
    @Value("#{jobParameters[datetime]}")
    private String datetime;

    // batch명
    @Value("#{jobParameters[job]}")
    private String job;
    
    // 비매칭 대리점 (universe)
    @Getter
    @Setter
    private String mismatchStoreUniverse;
    
    // 비매칭 대리점 (jupiter)
    @Getter
    @Setter
    private String mismatchStoreJupiter;

    @Getter
    @Setter
    private String saveFlag; // C: 생성, U: 수정
    
    @Getter
    @Setter
    private String storeId;

    
    public LocalDateTime getDatetime() {
        if (Objects.isNull(this.datetime) || this.datetime.isBlank()) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(this.datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
    
    public String getStartDate() {
        return this.getDatetime().truncatedTo(ChronoUnit.DAYS).minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getEndDate() {
        return this.getDatetime().truncatedTo(ChronoUnit.DAYS).minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public String getStartDatetime() {
        return this.getDatetime().withNano(0).minusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public String getEndDatetime() {
        return this.getDatetime().withNano(999999999).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
    
    public BusinessPartnerParameter(@Value("${mismatch.store.seq.universe}") String mismatchStoreUniverse,
            @Value("${mismatch.store.seq.jupiter}") String mismatchStoreJupiter) {
        this.mismatchStoreUniverse = mismatchStoreUniverse;
        this.mismatchStoreJupiter = mismatchStoreJupiter;
    }
}
