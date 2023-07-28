package biz.riman.erp.batch.dto.salesOrder;

import biz.riman.erp.batch.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InterfaceSalseOrderItemDto extends BaseDto {
    // universe, jupiter에서 사용하는 주문번호
	private String purchaseOrderByCustomer;
	// ERP에서 사용하는 주문번호
	private String salesOrder;
	// universe, jupiter에서 사용하는 주문아이템번호
	private String salesOrderProductId;
	// ERP에서 사용하는 주문아이템번호
	private String salesOrderItem;
	
    public InterfaceSalseOrderItemDto(String purchaseOrderByCustomer, String salesOrder) {
        super();
        this.purchaseOrderByCustomer = purchaseOrderByCustomer;
        this.salesOrder = salesOrder;
    }
	
}
