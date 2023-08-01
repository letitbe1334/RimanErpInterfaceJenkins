package biz.riman.erp.batch.mapper.salesOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.salesOrder.InterfaceSalseOrderDto;
import biz.riman.erp.batch.dto.salesOrder.SalesOrderItemDto;

@MapperConnection
public interface IfSalesOrderMapper {
    // I/F 주문 테이블 INSERT
    int saveInterfaceSalesOrder(InterfaceSalseOrderDto model) throws Exception;
    // I/F 주문상품 테이블 INSERT
    int saveInterfaceSalesOrderItem(@Param("items") List<SalesOrderItemDto> items
            ,@Param("purchaseOrderByCustomer") String purchaseOrderByCustomer
            ,@Param("salesOrder") String salesOrder
            ,@Param("resultStatus") String resultStatus
            ,@Param("httpStatus") int httpStatus
            ,@Param("exceptions") String exceptions) throws Exception;
}
