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
     * 상품 키값, 상품 패키지 키값(universe, jupiter)
     */
    @JsonIgnore
    private String salesOrderProductId;
    @JsonIgnore
    private String salesOrderProductPackageId;
}
