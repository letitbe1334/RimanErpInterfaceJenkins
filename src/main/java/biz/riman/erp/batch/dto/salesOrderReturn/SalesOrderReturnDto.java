package biz.riman.erp.batch.dto.salesOrderReturn;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SalesOrderReturnDto {
    @NotBlank(message = "CustomerReturnType must not be empty")
    @JsonProperty(value = "CustomerReturnType")
    private String CustomerReturnType;
    @NotBlank(message = "ReferenceSDDocument must not be empty")
    @JsonProperty(value = "ReferenceSDDocument")
	private String ReferenceSDDocument;
    @NotBlank(message = "SoldToParty must not be empty")
    @JsonProperty(value = "SoldToParty")
    private String SoldToParty;
    @NotBlank(message = "SDDocumentReason must not be empty")
    @JsonProperty(value = "SDDocumentReason")
    private String SDDocumentReason;
    
    /**
     * 배송비
     */
    @JsonIgnore
    private String ConditionType;
    @JsonIgnore
    private String ConditionRateValue;
    @JsonIgnore
    private String ConditionCurrency;
    

    @JsonProperty(value = "to_Item")
    private List<SalesOrderReturnItemDto> to_Item;
    @JsonProperty(value = "to_Pricing")
    private List<SalesOrderReturnPricingDto> to_Pricing;
    @JsonProperty(value = "to_ZPayment")
    private List<SalesOrderReturnPaymentDto> to_ZPayment;
    
    /**
     * 부가정보
     */
    @JsonIgnore
    private String returnOrderId;
    @JsonIgnore
    private String returnOrderByCustomer;
    @JsonIgnore
    private String salesOrderId;
    @JsonIgnore
    private String purchaseOrderByCustomer;
}
