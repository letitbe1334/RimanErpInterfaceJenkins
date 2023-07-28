package biz.riman.erp.batch.mapper.delivery;

import java.util.List;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import biz.riman.erp.batch.dto.delivery.DeliveryDocumentItemDto;
import biz.riman.erp.batch.parameter.DeliveryParameter;

@MapperConnection
public interface JupiterDeliveryMapper {
    // 출고 상품 조회
    List<DeliveryDocumentItemDto> getDeliveryDocumentItems(DeliveryParameter parameter) throws Exception;
}
