package biz.riman.erp.batch.dto.businessPartner;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 공급처 구매
 */
@Data
public class BusinessPartnerSupplierPurchasingDto {
    @NotBlank(message = "PurchasingOrganization must not be empty")
    @JsonProperty(value = "PurchasingOrganization")
    private String PurchasingOrganization;
    @NotBlank(message = "PurchaseOrderCurrency must not be empty")
    @JsonProperty(value = "PurchaseOrderCurrency")
    private String PurchaseOrderCurrency;
    @NotBlank(message = "PaymentTerms must not be empty")
    @JsonProperty(value = "PaymentTerms")
    private String PaymentTerms;
    @NotBlank(message = "InvoiceIsGoodsReceiptBased must not be empty")
    @JsonProperty(value = "InvoiceIsGoodsReceiptBased")
    private boolean InvoiceIsGoodsReceiptBased;
}
