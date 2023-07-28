package biz.riman.erp.batch.dto.delivery;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 출고 상품
 */
@Data
public class DeliveryDocumentItemDto {
    @NotBlank(message = "ReferenceSDDocument must not be empty")
    @JsonProperty(value = "ReferenceSDDocument")
	private String ReferenceSDDocument;
    @NotBlank(message = "ReferenceSDDocumentItem must not be empty")
    @JsonProperty(value = "ReferenceSDDocumentItem")
	private String ReferenceSDDocumentItem;
    @NotBlank(message = "ActualDeliveryQuantity must not be empty")
    @JsonProperty(value = "ActualDeliveryQuantity")
	private String ActualDeliveryQuantity;
    @NotBlank(message = "DeliveryQuantityUnit must not be empty")
    @JsonProperty(value = "DeliveryQuantityUnit")
	private String DeliveryQuantityUnit;
    
    /**
     * 부가정보
     */
    @JsonIgnore
    private String purchaseOrderByCustomer;
    @JsonIgnore
    private String salesOrderProductId;
    @JsonIgnore
    private String productPackageProductId;
}
