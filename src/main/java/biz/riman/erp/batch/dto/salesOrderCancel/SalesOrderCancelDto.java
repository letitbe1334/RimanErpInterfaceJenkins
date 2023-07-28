package biz.riman.erp.batch.dto.salesOrderCancel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SalesOrderCancelDto {
//    @NotBlank(message = "SalesOrder must not be empty")
    @JsonProperty(value = "SalesOrder")
    private String SalesOrder;
    @JsonProperty(value = "PurchaseOrderByCustomer")
    private String PurchaseOrderByCustomer;
    @JsonIgnore
    private String salesOrderId;
    @JsonIgnore
    private String paymentId;
    
    /**
     * 쿠폰 정보
     */
    @JsonIgnore
    private String ConditionType;
    @JsonIgnore
    private String ConditionRateValue;
    @JsonIgnore
    private String ConditionCurrency;
    
    @JsonProperty(value = "to_Pricing")
    private List<SalesOrderCancelPricingDto> to_Pricing;
    @JsonProperty(value = "to_Item")
    private List<SalesOrderCancelItemDto> to_Item;
    @JsonProperty(value = "to_ZPayment")
    private List<SalesOrderCancelPaymentDto> to_ZPayment;
}
