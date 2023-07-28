package biz.riman.erp.batch.dto;

import lombok.Data;

@Data
public class BatchDto {
    // 배치아이디
    private String batchId;

    // 배치 클래스명
    private String batchClassName;

    // 배치명
    private String batchName;

    // 크론 표현식
    private String cronOption;

    // 사용여부
    private String useFlag;
}
