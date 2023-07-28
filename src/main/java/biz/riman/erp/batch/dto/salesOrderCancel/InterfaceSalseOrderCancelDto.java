package biz.riman.erp.batch.dto.salesOrderCancel;

import biz.riman.erp.batch.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InterfaceSalseOrderCancelDto extends BaseDto {
    // universe, jupiter에서 사용하는 주문번호
	private String purchaseOrderByCustomer;
	// ERP에서 사용하는 주문번호
	private String salesOrder;
    // 성공여부 (Y, N, null)
    private String resultStatus;
    // http 상태코드
    private int httpStatus;
    // 에러
    private String exceptions;
    

    public InterfaceSalseOrderCancelDto(String purchaseOrderByCustomer, String salesOrder, String resultStatus,
            int httpStatus, String exceptions) {
        super();
        this.purchaseOrderByCustomer = purchaseOrderByCustomer;
        this.salesOrder = salesOrder;
        this.resultStatus = resultStatus;
        this.httpStatus = httpStatus;
        this.exceptions = exceptions;
    }
    
}
