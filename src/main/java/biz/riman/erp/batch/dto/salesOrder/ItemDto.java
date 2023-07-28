package biz.riman.erp.batch.dto.salesOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemDto {
    @JsonProperty(value = "SalesOrderItem")
	private String SalesOrderItem;
    @JsonProperty(value = "Material")
	private String Material;
    @JsonProperty(value = "RequestedQuantity")
	private String RequestedQuantity;
    @JsonProperty(value = "RequestedQuantityUnit")
	private String RequestedQuantityUnit;
	
	public ItemDto(String salesOrderItem, String material, String requestedQuantity, String requestedQuantityUnit) {
		super();
		this.SalesOrderItem = salesOrderItem;
		this.Material = material;
		this.RequestedQuantity = requestedQuantity;
		this.RequestedQuantityUnit = requestedQuantityUnit;
	}
}
