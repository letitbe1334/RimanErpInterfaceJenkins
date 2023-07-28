package biz.riman.erp.batch.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BatchHisDto {
    
    // 배치이력ID
    private String batchHisId;

    // 배치 클래스명
    private String batchClassName;

    // 배치명
    private String batchName;

    // 성공여부
    private String successYn;

    // 에러
    private String exceptions;
    
    // 실행일시
    private String startDatetime;
    
    // 종료일시
    private String endDatetime;

    public BatchHisDto(String batchClassName, String batchName) {
        super();
        this.batchClassName = batchClassName;
        this.batchName = batchName;
        this.successYn = "Y";
        this.startDatetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
    
}
