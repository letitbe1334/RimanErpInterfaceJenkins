package biz.riman.erp.batch.mapper.salesOrderCancel;

import java.util.List;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.salesOrderCancel.SalesOrderCancelDto;
import biz.riman.erp.batch.dto.salesOrderCancel.SalesOrderCancelItemDto;
import biz.riman.erp.batch.dto.salesOrderCancel.SalesOrderCancelPaymentDto;
import biz.riman.erp.batch.parameter.SalesOrderCancelParameter;

@MapperConnection
public interface UniverseSalesOrderCancelMapper {
    // 취소주문정보 조회
    List<SalesOrderCancelDto> getSalesOrderCancels(SalesOrderCancelParameter parameter) throws Exception;
    // 취소주문 항목 정보 조회
    List<SalesOrderCancelItemDto> getSalesOrderCancelItems(SalesOrderCancelParameter parameter) throws Exception;
    // 취소주문 결제 정보 조회
    List<SalesOrderCancelPaymentDto> getSalesOrderCancelPayments(SalesOrderCancelParameter parameter) throws Exception;
}
