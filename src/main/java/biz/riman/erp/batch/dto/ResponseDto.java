package biz.riman.erp.batch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * API response 통합
 */
@Data
public class ResponseDto {
    /**
     * S: success(성공)
     * F: Fail(실패)
     */
    @JsonProperty(value = "resultStatus")
    private String resultStatus;
    /**
     * error 원인에 대한 상세 내용(코드)
     * 
     * ALREADY_EXIST, ...
     */
    @JsonProperty(value = "resultStatusDetail")
    private String resultStatusDetail;
    /**
     * error 원인
     */
    @JsonProperty(value = "message")
    private String message;
    /**
     * ERP 생성 값 반환 
     * 
     * 주문생성인 경우 : SalesOrder
     * 반품인 경우 : ReturnOrder
     */
    @JsonProperty(value = "referenceNo")
    private String referenceNo;
    /**
     * MPL id (SAP)
     */
    @JsonProperty(value = "sapMessageProcessingLogId")
    private String sapMessageProcessingLogId;
}
 