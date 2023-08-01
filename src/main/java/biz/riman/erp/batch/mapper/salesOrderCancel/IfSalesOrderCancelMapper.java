package biz.riman.erp.batch.mapper.salesOrderCancel;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.salesOrderCancel.InterfaceSalseOrderCancelDto;

@MapperConnection
public interface IfSalesOrderCancelMapper {
    // I/F 테이블 INSERT
    int saveInterfaceSalesOrderCancel(InterfaceSalseOrderCancelDto model) throws Exception;
}
