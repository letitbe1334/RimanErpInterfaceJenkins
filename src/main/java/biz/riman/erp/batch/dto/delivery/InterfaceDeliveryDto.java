package biz.riman.erp.batch.dto.delivery;

import biz.riman.erp.batch.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InterfaceDeliveryDto extends BaseDto {
    // universe, jupiter에서 사용하는 주문번호
    private String purchaseOrderByCustomer;
    // ERP에서 사용하는 주문번호
    private String salesOrder;
    // universe, jupiter에서 사용하는 주문아이템번호
    private String salesOrderProductId;
    // ERP에서 사용하는 주문아이템번호
    private String salesOrderItem;
    // ERP에서 사용하는 출하번호
    private String deliveryOrder;
	// 성공여부 (Y, N, null)
    private String resultStatus;
    // http 상태코드
    private int httpStatus;
    // 에러
    private String exceptions;
    // MPL id (SAP)
    private String sapMessageProcessingLogId;
    
    public InterfaceDeliveryDto(String resultStatus, int httpStatus, String exceptions,
    		String sapMessageProcessingLogId) {
        super();
        this.resultStatus = resultStatus;
        this.httpStatus = httpStatus;
        this.exceptions = exceptions;
        this.sapMessageProcessingLogId = sapMessageProcessingLogId;
    }
    
}
