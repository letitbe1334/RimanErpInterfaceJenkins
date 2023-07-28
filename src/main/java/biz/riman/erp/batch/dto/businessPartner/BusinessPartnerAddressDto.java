package biz.riman.erp.batch.dto.businessPartner;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 주소
 */
@Data
public class BusinessPartnerAddressDto {
    @NotBlank(message = "Country must not be empty")
    @JsonProperty(value = "Country")
    private String Country;
    @NotBlank(message = "CityName must not be empty")
    @JsonProperty(value = "CityName")
    private String CityName;
    @NotBlank(message = "StreetName must not be empty")
    @JsonProperty(value = "StreetName")
    private String StreetName;
    @JsonProperty(value = "HouseNumber")
    private String HouseNumber;
    @NotBlank(message = "PostalCode must not be empty")
    @JsonProperty(value = "PostalCode")
    private String PostalCode;
    @NotBlank(message = "Language must not be empty")
    @JsonProperty(value = "Language")
    private String Language;
    
    public BusinessPartnerAddressDto(BusinessPartnerDto bp) {
        super();
        BeanUtils.copyProperties(bp, this);
        this.Language = bp.getAddressLanguage();
    }
    
}
