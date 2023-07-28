package biz.riman.erp.batch.dto.salesOrderReturn;

import biz.riman.erp.batch.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InterfaceSalseOrderReturnDto extends BaseDto {
    // universe, jupiter에서 사용하는 주문번호
	private String purchaseOrderByCustomer;
	// universe, jupiter에서 사용하는 반품번호
	private String returnOrderByCustomer;
	// ERP에서 사용하는 주문번호
	private String salesOrder;
	// ERP에서 사용하는 주문번호
	private String returnOrder;
    // 성공여부 (Y, N, null)
    private String resultStatus;
    // http 상태코드
    private int httpStatus;
    // 에러
    private String exceptions;

    public InterfaceSalseOrderReturnDto(String purchaseOrderByCustomer, String returnOrderByCustomer, String salesOrder, String returnOrder,
            String resultStatus, int httpStatus, String exceptions) {
        super();
        this.purchaseOrderByCustomer = purchaseOrderByCustomer;
        this.returnOrderByCustomer = returnOrderByCustomer;
        this.salesOrder = salesOrder;
        this.returnOrder = returnOrder;
        this.resultStatus = resultStatus;
        this.httpStatus = httpStatus;
        this.exceptions = exceptions;
    }
    
}
