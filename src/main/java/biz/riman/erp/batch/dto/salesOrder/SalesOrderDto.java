package biz.riman.erp.batch.dto.salesOrder;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 주문생성
 */
@Data
public class SalesOrderDto {
    @NotBlank(message = "SenderBusinessSystemName must not be empty")
    @JsonProperty(value = "SenderBusinessSystemName")
    private String SenderBusinessSystemName;
    @NotBlank(message = "PurchaseOrderByCustomer must not be empty")
    @JsonProperty(value = "PurchaseOrderByCustomer")
    private String PurchaseOrderByCustomer;
    @NotBlank(message = "SalesOrderType must not be empty")
    @JsonProperty(value = "SalesOrderType")
    private String SalesOrderType;
    @NotBlank(message = "SalesOrganization must not be empty")
    @JsonProperty(value = "SalesOrganization")
    private String SalesOrganization;
    @NotBlank(message = "DistributionChannel must not be empty")
    @JsonProperty(value = "DistributionChannel")
    private String DistributionChannel;
    @NotBlank(message = "ZSalesType must not be empty")
    @JsonProperty(value = "ZSalesType")
    private String ZSalesType;
    @NotBlank(message = "SoldToParty must not be empty")
    @JsonProperty(value = "SoldToParty")
    private String SoldToParty;
    @NotBlank(message = "RequestedDeliveryDate must not be empty")
    @JsonProperty(value = "RequestedDeliveryDate")
    private String RequestedDeliveryDate;
    @JsonProperty(value = "CustomerPurchaseOrderDate")
    private String CustomerPurchaseOrderDate;
    @JsonProperty(value = "SalesOrderDate")
    private String SalesOrderDate;
//  @JsonIgnore
    @NotBlank(message = "AdditionalCustomerGroup3 must not be empty")
    @JsonProperty(value = "AdditionalCustomerGroup3")
    private String AdditionalCustomerGroup3;
    @JsonProperty(value = "ZBINC")
    private String ZBINC;
    @JsonProperty(value = "ZTINC")
    private String ZTINC;
    @JsonProperty(value = "ZRECOM")
    private String ZRECOM;
    @JsonProperty(value = "ZGRECOM")
    private String ZGRECOM;
    @JsonProperty(value = "to_Pricing")
    private List<PricingDto> to_Pricing;
    @JsonProperty(value = "to_Item")
    private List<SalesOrderItemDto> to_Item;
    @JsonProperty(value = "to_ZPayment")
    private List<PaymentDto> to_ZPayment;
    
    /**
     * 배송비 / 쿠폰
     */
    @JsonIgnore
    private String DeliveryType;
    @JsonIgnore
    private String DeliveryRateValue;
    @JsonIgnore
    private String CouponType;
    @JsonIgnore
    private String CouponRateValue;
    @JsonIgnore
    private String CouponVatType;
    @JsonIgnore
    private String CouponRateVatValue;
    
    /**
     * 하위 테이블 조회용
     */
    @JsonIgnore
    private String salesOrderId;
    @JsonIgnore
    private String paymentId;
    
}
