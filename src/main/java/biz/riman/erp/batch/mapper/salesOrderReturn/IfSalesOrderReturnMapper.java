package biz.riman.erp.batch.mapper.salesOrderReturn;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.salesOrderReturn.InterfaceSalseOrderReturnDto;

@MapperConnection
public interface IfSalesOrderReturnMapper {
    // I/F 주문 테이블 INSERT
    int saveInterfaceSalesOrderReturn(InterfaceSalseOrderReturnDto model) throws Exception;
}
