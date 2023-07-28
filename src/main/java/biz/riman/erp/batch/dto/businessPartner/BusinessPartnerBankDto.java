package biz.riman.erp.batch.dto.businessPartner;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 은행
 */
@Data
public class BusinessPartnerBankDto {
    @JsonProperty(value = "BankIdentification")
    private String BankIdentification;
    @JsonProperty(value = "BankCountryKey")
    private String BankCountryKey;
    @JsonProperty(value = "BankNumber")
    private String BankNumber;
    @JsonProperty(value = "BankAccount")
    private String BankAccount;
    @JsonProperty(value = "BankAccountHolderName")
    private String BankAccountHolderName;

    public BusinessPartnerBankDto(BusinessPartnerDto bp) {
        super();
        BeanUtils.copyProperties(bp, this);
    }
    
}
