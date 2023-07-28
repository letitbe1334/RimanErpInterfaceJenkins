package biz.riman.erp.batch.dto.businessPartner;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 공급처 회사
 */
@Data
public class BusinessPartnerSupplierCompanyDto {
    @NotBlank(message = "CompanyCode must not be empty")
    @JsonProperty(value = "CompanyCode")
    private String CompanyCode;
    @NotBlank(message = "ReconciliationAccount must not be empty")
    @JsonProperty(value = "ReconciliationAccount")
    private String ReconciliationAccount;
    @NotBlank(message = "PaymentTerms must not be empty")
    @JsonProperty(value = "PaymentTerms")
    private String PaymentTerms;
}
