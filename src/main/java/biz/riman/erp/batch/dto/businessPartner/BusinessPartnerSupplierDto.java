package biz.riman.erp.batch.dto.businessPartner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BP 공급처
 */
@Data
public class BusinessPartnerSupplierDto {
    @JsonProperty(value = "to_SupplierPurchasingOrg")
    private List<BusinessPartnerSupplierPurchasingDto> to_SupplierPurchasingOrg;
    @JsonProperty(value = "to_SupplierCompany")
    private List<BusinessPartnerSupplierCompanyDto> to_SupplierCompany;
    
    public BusinessPartnerSupplierDto(BusinessPartnerDto bp) {
        super();
        
        this.to_SupplierPurchasingOrg = new ArrayList<BusinessPartnerSupplierPurchasingDto>();
        BusinessPartnerSupplierPurchasingDto supplierPurchasing = new BusinessPartnerSupplierPurchasingDto();
        BeanUtils.copyProperties(bp, supplierPurchasing);
        supplierPurchasing.setPaymentTerms(bp.getPurchasePaymentTerms());
        to_SupplierPurchasingOrg.add(supplierPurchasing);
        
        this.to_SupplierCompany = new ArrayList<BusinessPartnerSupplierCompanyDto>();
        BusinessPartnerSupplierCompanyDto supplierCompany = new BusinessPartnerSupplierCompanyDto();
        supplierCompany.setCompanyCode(bp.getSupplierCompanyCode());
        supplierCompany.setPaymentTerms(bp.getSupplierPaymentTerms());
        supplierCompany.setReconciliationAccount(bp.getSupplierReconciliationAccount());
        to_SupplierCompany.add(supplierCompany);
    }
    
}
