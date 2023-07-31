package biz.riman.erp.batch.dto.businessPartner;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 마스터
 */
@Data
public class BusinessPartnerDto {
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
    
    /**
     * BP 주소
     */
    @JsonProperty(value = "Country")
    private String Country;
    @JsonProperty(value = "CityName")
    private String CityName;
    @JsonProperty(value = "StreetName")
    private String StreetName;
    @JsonProperty(value = "HouseNumber")
    private String HouseNumber;
    @JsonProperty(value = "PostalCode")
    private String PostalCode;
    @JsonProperty(value = "AddressLanguage")
    private String AddressLanguage; // 중복으로 인한 변경
    
    /**
     * BP 고객사 판매지역
     */
    @JsonProperty(value = "SalesOrganization")
    private String SalesOrganization;
    @JsonProperty(value = "DistributionChannel")
    private String DistributionChannel;
    @JsonProperty(value = "Division")
    private String Division;
    @JsonProperty(value = "ShippingCondition")
    private String ShippingCondition;
    @JsonProperty(value = "IncotermsClassification")
    private String IncotermsClassification;
    @JsonProperty(value = "IncotermsLocation1")
    private String IncotermsLocation1;
    @JsonProperty(value = "CustomerPaymentTerms")
    private String CustomerPaymentTerms;
    @JsonProperty(value = "Currency")
    private String Currency;
    @JsonProperty(value = "CustomerAccountAssignmentGroup")
    private String CustomerAccountAssignmentGroup;
    
    /**
     * BP 고객사 판매지역 > 세금
     */
    @JsonProperty(value = "DepartureCountry")
    private String DepartureCountry = "KR";
    @JsonProperty(value = "CustomerTaxCategory")
    private String CustomerTaxCategory = "TTX1";
    @JsonProperty(value = "CustomerTaxClassification")
    private String CustomerTaxClassification = "1";
    
    
    /**
     * BP 고객사 회사
     */
    @JsonProperty(value = "CustomCompanyCode")
    private String CustomCompanyCode; // 중복으로 인한 변경
    @JsonProperty(value = "CustomReconciliationAccount")
    private String CustomReconciliationAccount; // 중복으로 인한 변경
    @JsonProperty(value = "CustomPaymentTerms")
    private String CustomPaymentTerms; // 중복으로 인한 변경
    
    /**
     * BP 세금
     */
    @NotBlank(message = "BPTaxType must not be empty")
    @JsonProperty(value = "BPTaxType")
    private String BPTaxType;
    @NotBlank(message = "BPTaxNumber must not be empty")
    @JsonProperty(value = "BPTaxNumber")
    private String BPTaxNumber;
    
    /**
     * BP 은행
     */
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
    
    /**
     * BP 공급처 구매
     */
    @JsonProperty(value = "PurchasingOrganization")
    private String PurchasingOrganization;
    @JsonProperty(value = "PurchaseOrderCurrency")
    private String PurchaseOrderCurrency;
    @JsonProperty(value = "PurchasePaymentTerms")
    private String PurchasePaymentTerms; // 중복으로 인한 변경
    @JsonProperty(value = "InvoiceIsGoodsReceiptBased")
    private boolean InvoiceIsGoodsReceiptBased;
    
    /**
     * BP 공급처 회사
     */
    @JsonProperty(value = "SupplierCompanyCode")
    private String SupplierCompanyCode; // 중복으로 인한 변경
    @JsonProperty(value = "SupplierReconciliationAccount")
    private String SupplierReconciliationAccount; // 중복으로 인한 변경
    @JsonProperty(value = "SupplierPaymentTerms")
    private String SupplierPaymentTerms; // 중복으로 인한 변경
}
