package biz.riman.erp.batch.parameter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@JobScope
@Component
public class SalesOrderParameter {
    
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
    
    // universe, jupiter에서 사용하는 주문번호
    @Getter
    @Setter
    private String purchaseOrderByCustomer;
    
    // 주문id
    @Getter
    @Setter
    private String salesOrderId;
    
    // 주문결제id
    @Getter
    @Setter
    private String paymentId;

    
    public LocalDateTime getDatetime() {
        if (Objects.isNull(this.datetime) || this.datetime.isBlank()) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(this.datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
    
    public String getStartDatetime() {
        return this.getDatetime().withNano(0).minusMinutes(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public String getEndDatetime() {
        return this.getDatetime().withNano(999999999).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
    
    public SalesOrderParameter(@Value("${mismatch.store.id.universe}") String mismatchStoreUniverse,
            @Value("${mismatch.store.seq.jupiter}") String mismatchStoreJupiter) {
        this.mismatchStoreUniverse = mismatchStoreUniverse;
        this.mismatchStoreJupiter = mismatchStoreJupiter;
    }
    
}
