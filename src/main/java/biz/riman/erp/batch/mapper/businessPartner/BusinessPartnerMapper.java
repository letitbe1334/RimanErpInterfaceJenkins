package biz.riman.erp.batch.mapper.businessPartner;

import java.util.List;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerDto;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerRoleDto;
import biz.riman.erp.batch.parameter.BusinessPartnerParameter;

@MapperConnection
public interface BusinessPartnerMapper {
    // BP 전체 조회
    List<BusinessPartnerDto> getBusinessPartners(BusinessPartnerParameter parameter) throws Exception;
    // BP 역할 조회
    List<BusinessPartnerRoleDto> getBusinessPartnerRolses(BusinessPartnerParameter parameter) throws Exception;
}
