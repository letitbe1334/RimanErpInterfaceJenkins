package biz.riman.erp.batch.dto.delivery;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 출고 상품
 */
@Data
public class DeliveryDocumentDto {
    @JsonProperty(value = "to_Item")
    private List<DeliveryDocumentItemDto> to_Item;

    public DeliveryDocumentDto(List<DeliveryDocumentItemDto> to_Item) {
        super();
        this.to_Item = to_Item;
    }
    
}
