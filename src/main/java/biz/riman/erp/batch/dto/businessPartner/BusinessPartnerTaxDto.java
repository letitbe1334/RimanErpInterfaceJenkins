package biz.riman.erp.batch.dto.businessPartner;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 세금
 */
@Data
public class BusinessPartnerTaxDto {
    @NotBlank(message = "BPTaxType must not be empty")
    @JsonProperty(value = "BPTaxType")
    private String BPTaxType;
    @NotBlank(message = "BPTaxNumber must not be empty")
    @JsonProperty(value = "BPTaxNumber")
    private String BPTaxNumber;

    public BusinessPartnerTaxDto(BusinessPartnerDto bp) {
        super();
        BeanUtils.copyProperties(bp, this);
    }
}
