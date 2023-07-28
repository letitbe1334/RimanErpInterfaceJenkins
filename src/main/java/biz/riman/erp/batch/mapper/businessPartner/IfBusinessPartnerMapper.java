package biz.riman.erp.batch.mapper.businessPartner;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.businessPartner.InterfaceBusinessPartnerDto;

@MapperConnection
public interface IfBusinessPartnerMapper {
    // I/F BP 테이블 INSERT
    int insertInterfaceBusinessPartner(InterfaceBusinessPartnerDto model) throws Exception;
}
