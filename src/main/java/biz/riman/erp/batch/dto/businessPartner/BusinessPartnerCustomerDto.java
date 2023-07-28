package biz.riman.erp.batch.dto.businessPartner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 고객사
 */
@Data
public class BusinessPartnerCustomerDto {
    @JsonProperty(value = "to_CustomerSalesArea")
    private List<BusinessPartnerCustomerSalesAreaDto> to_CustomerSalesArea;
    @JsonProperty(value = "to_CustomerCompany")
    private List<BusinessPartnerCustomerCompanyDto> to_CustomerCompany;
    
    public BusinessPartnerCustomerDto(BusinessPartnerDto bp) {
        super();
        this.to_CustomerSalesArea = new ArrayList<BusinessPartnerCustomerSalesAreaDto>();
        BusinessPartnerCustomerSalesAreaDto customerSalesArea = new BusinessPartnerCustomerSalesAreaDto();
        BeanUtils.copyProperties(bp, customerSalesArea);
        this.to_CustomerSalesArea.add(customerSalesArea);
        
        this.to_CustomerCompany = new ArrayList<BusinessPartnerCustomerCompanyDto>();
        BusinessPartnerCustomerCompanyDto customerCompany = new BusinessPartnerCustomerCompanyDto();
        BeanUtils.copyProperties(bp, customerCompany);
        customerCompany.setCompanyCode(bp.getCustomCompanyCode());
        customerCompany.setReconciliationAccount(bp.getCustomReconciliationAccount());
        customerCompany.setPaymentTerms(bp.getCustomPaymentTerms());
        this.to_CustomerCompany.add(customerCompany);
    }
    
}
