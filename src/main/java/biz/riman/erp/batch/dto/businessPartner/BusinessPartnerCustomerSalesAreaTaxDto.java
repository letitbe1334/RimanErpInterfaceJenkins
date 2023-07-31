package biz.riman.erp.batch.dto.businessPartner;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 고객사 판매지역 > 세금
 */
@Data
public class BusinessPartnerCustomerSalesAreaTaxDto {
    @NotBlank(message = "SalesOrganization must not be empty")
    @JsonProperty(value = "SalesOrganization")
    private String SalesOrganization;
    @NotBlank(message = "DistributionChannel must not be empty")
    @JsonProperty(value = "DistributionChannel")
    private String DistributionChannel;
    @NotBlank(message = "Division must not be empty")
    @JsonProperty(value = "Division")
    private String Division;
    @JsonProperty(value = "DepartureCountry")
    private String DepartureCountry;
    @JsonProperty(value = "CustomerTaxCategory")
    private String CustomerTaxCategory;
    @JsonProperty(value = "CustomerTaxClassification")
    private String CustomerTaxClassification;
}
