package biz.riman.erp.batch.mapper.delivery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.delivery.DeliveryDocumentItemDto;
import biz.riman.erp.batch.dto.delivery.InterfaceDeliveryDto;

@MapperConnection
public interface IfDeliveryMapper {
    // I/F 출고 테이블 INSERT
    int insertInterfaceDeliveries(@Param("items") List<DeliveryDocumentItemDto> items, @Param("delivery") InterfaceDeliveryDto model) throws Exception;
}
