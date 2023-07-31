package biz.riman.erp.batch.dto.salesOrder;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import biz.riman.erp.batch.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SalesOrderItemDto extends BaseDto {
    @NotBlank(message = "SalesOrderItem must not be empty")
    @JsonProperty(value = "SalesOrderItem")
	private String SalesOrderItem;
    @NotBlank(message = "Material must not be empty")
    @JsonProperty(value = "Material")
	private String Material;
    @NotBlank(message = "RequestedQuantity must not be empty")
    @JsonProperty(value = "RequestedQuantity")
	private String RequestedQuantity;
    @NotBlank(message = "RequestedQuantityUnit must not be empty")
    @JsonProperty(value = "RequestedQuantityUnit")
	private String RequestedQuantityUnit;
    
    /**
     * 제품 키값(universe, jupiter)
     */
    @JsonIgnore
    private String productId;
    
    /**
     * 배송비 여부
     */
    @JsonIgnore
    private String deliveryFlag;

	public SalesOrderItemDto(@NotBlank(message = "SalesOrderItem must not be empty") String salesOrderItem,
			@NotBlank(message = "Material must not be empty") String material,
			@NotBlank(message = "RequestedQuantity must not be empty") String requestedQuantity,
			@NotBlank(message = "RequestedQuantityUnit must not be empty") String requestedQuantityUnit,
			String deliveryFlag) {
		super();
		SalesOrderItem = salesOrderItem;
		Material = material;
		RequestedQuantity = requestedQuantity;
		RequestedQuantityUnit = requestedQuantityUnit;
		this.deliveryFlag = deliveryFlag;
	}
    
}
