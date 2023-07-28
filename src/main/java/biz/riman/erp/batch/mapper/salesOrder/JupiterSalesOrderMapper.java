package biz.riman.erp.batch.mapper.salesOrder;

import java.util.List;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.salesOrder.PaymentDto;
import biz.riman.erp.batch.dto.salesOrder.SalesOrderDto;
import biz.riman.erp.batch.dto.salesOrder.SalesOrderItemDto;
import biz.riman.erp.batch.parameter.SalesOrderParameter;

@MapperConnection
public interface JupiterSalesOrderMapper {
    // 주문정보 조회
    List<SalesOrderDto> getSalesOrders(SalesOrderParameter parameter) throws Exception;
    // 주문 항목 정보 조회
    List<SalesOrderItemDto> getSalesOrderItems(SalesOrderParameter parameter) throws Exception;
    // 주문 결제 정보 조회
    List<PaymentDto> getSalesOrderPayments(SalesOrderParameter parameter) throws Exception;
}
