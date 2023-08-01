package biz.riman.erp.batch.dto.salesOrder;

import biz.riman.erp.batch.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InterfaceSalseOrderDto extends BaseDto {
    // universe, jupiter에서 사용하는 주문번호
	private String purchaseOrderByCustomer;
	// ERP에서 사용하는 주문번호
	private String salesOrder;
	// 성공여부 (Y, N, null)
    private String resultStatus;
    // 상세 내용(코드)
    private String resultStatusDetail;
    // http 상태코드
    private int httpStatus;
    // 에러
    private String exceptions;
    // MPL id (SAP)
    private String sapMessageProcessingLogId;
    
    public InterfaceSalseOrderDto(String purchaseOrderByCustomer) {
        super();
        this.purchaseOrderByCustomer = purchaseOrderByCustomer;
    }

    public InterfaceSalseOrderDto(String purchaseOrderByCustomer, String salesOrder, String resultStatus,
            String resultStatusDetail, int httpStatus, String exceptions, String sapMessageProcessingLogId) {
        super();
        this.purchaseOrderByCustomer = purchaseOrderByCustomer;
        this.salesOrder = salesOrder;
        this.resultStatus = resultStatus;
        this.resultStatusDetail = resultStatusDetail;
        this.httpStatus = httpStatus;
        this.exceptions = exceptions;
        this.sapMessageProcessingLogId = sapMessageProcessingLogId;
    }
    
}
