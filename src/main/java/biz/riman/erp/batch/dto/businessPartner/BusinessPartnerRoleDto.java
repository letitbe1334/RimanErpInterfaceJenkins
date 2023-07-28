package biz.riman.erp.batch.dto.businessPartner;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 역할
 */
@Data
public class BusinessPartnerRoleDto {
    @NotBlank(message = "BusinessPartnerRole must not be empty")
    @JsonProperty(value = "BusinessPartnerRole")
    private String BusinessPartnerRole;
}
