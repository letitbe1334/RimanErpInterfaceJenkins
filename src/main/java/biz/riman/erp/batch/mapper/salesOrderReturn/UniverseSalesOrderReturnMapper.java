package biz.riman.erp.batch.mapper.salesOrderReturn;

import java.util.List;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.salesOrderReturn.SalesOrderReturnDto;
import biz.riman.erp.batch.dto.salesOrderReturn.SalesOrderReturnItemDto;
import biz.riman.erp.batch.dto.salesOrderReturn.SalesOrderReturnPaymentDto;
import biz.riman.erp.batch.parameter.SalesOrderReturnParameter;

@MapperConnection
public interface UniverseSalesOrderReturnMapper {
    // 반품정보 조회
    List<SalesOrderReturnDto> getSalesOrderReturns(SalesOrderReturnParameter parameter) throws Exception;
    // 반품상품정보 조회
    List<SalesOrderReturnItemDto> getSalesOrderReturnItems(SalesOrderReturnParameter parameter) throws Exception;
    // 반품결제정보 조회
    List<SalesOrderReturnPaymentDto> getSalesOrderReturnPayments(SalesOrderReturnParameter parameter) throws Exception;
}
