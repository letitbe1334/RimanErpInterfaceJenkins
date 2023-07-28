package biz.riman.erp.batch.dto.salesOrderCancel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SalesOrderCancelItemDto {
    @JsonProperty(value = "SalesOrderItem")
	private String SalesOrderItem;
    @JsonProperty(value = "SalesDocumentRjcnReason")
    private String SalesDocumentRjcnReason;
}
