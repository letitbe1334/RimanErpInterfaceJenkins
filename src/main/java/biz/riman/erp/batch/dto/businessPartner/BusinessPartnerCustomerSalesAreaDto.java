package biz.riman.erp.batch.dto.businessPartner;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 고객사 판매지역
 */
@Data
public class BusinessPartnerCustomerSalesAreaDto {
    @NotBlank(message = "SalesOrganization must not be empty")
    @JsonProperty(value = "SalesOrganization")
    private String SalesOrganization;
    @NotBlank(message = "DistributionChannel must not be empty")
    @JsonProperty(value = "DistributionChannel")
    private String DistributionChannel;
    @NotBlank(message = "Division must not be empty")
    @JsonProperty(value = "Division")
    private String Division;
    @NotBlank(message = "ShippingCondition must not be empty")
    @JsonProperty(value = "ShippingCondition")
    private String ShippingCondition;
    @NotBlank(message = "IncotermsClassification must not be empty")
    @JsonProperty(value = "IncotermsClassification")
    private String IncotermsClassification;
    @NotBlank(message = "IncotermsLocation1 must not be empty")
    @JsonProperty(value = "IncotermsLocation1")
    private String IncotermsLocation1;
    @NotBlank(message = "CustomerPaymentTerms must not be empty")
    @JsonProperty(value = "CustomerPaymentTerms")
    private String CustomerPaymentTerms;
    @NotBlank(message = "Currency must not be empty")
    @JsonProperty(value = "Currency")
    private String Currency;
    @NotBlank(message = "CustomerAccountAssignmentGroup must not be empty")
    @JsonProperty(value = "CustomerAccountAssignmentGroup")
    private String CustomerAccountAssignmentGroup;
}
