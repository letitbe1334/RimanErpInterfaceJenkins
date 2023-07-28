package biz.riman.erp.batch.dto.businessPartner;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 마스터
 */
@Data
public class BusinessPartnerParamDto {
    @NotBlank(message = "BusinessPartner must not be empty")
    @JsonProperty(value = "BusinessPartner")
    private String BusinessPartner;
    @JsonProperty(value = "OrganizationBPName1")
    private String OrganizationBPName1;
    @JsonProperty(value = "BusinessPartnerGrouping")
    private String BusinessPartnerGrouping;
    @JsonProperty(value = "BusinessPartnerCategory")
    private String BusinessPartnerCategory;
    @JsonProperty(value = "Language")
    private String Language;
    @JsonProperty(value = "IsMarkedForArchiving")
    private boolean IsMarkedForArchiving;
//    @JsonProperty(value = "ZTEMP11")
//    private String ZTEMP11;
//    @JsonProperty(value = "ZTEMP12")
//    private String ZTEMP12;
//    @JsonProperty(value = "ZTEMP13")
//    private String ZTEMP13;
//    @JsonProperty(value = "ZTEMP14")
//    private String ZTEMP14;
//    @JsonProperty(value = "ZTEMP15")
//    private String ZTEMP15;
    
    @JsonProperty(value = "to_BusinessPartnerAddress")
    private List<BusinessPartnerAddressDto> to_BusinessPartnerAddress;
    @JsonProperty(value = "to_BusinessPartnerRole")
    private List<BusinessPartnerRoleDto> to_BusinessPartnerRole;
    @JsonProperty(value = "to_Customer")
    private BusinessPartnerCustomerDto to_Customer;
    @JsonProperty(value = "to_BusinessPartnerTax")
    private List<BusinessPartnerTaxDto> to_BusinessPartnerTax;
    @JsonProperty(value = "to_BusinessPartnerBank")
    private List<BusinessPartnerBankDto> to_BusinessPartnerBank;
    @JsonProperty(value = "to_Supplier")
    private BusinessPartnerSupplierDto to_Supplier;
            
}
