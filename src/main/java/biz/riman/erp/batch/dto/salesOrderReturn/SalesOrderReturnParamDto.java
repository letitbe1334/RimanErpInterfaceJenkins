package biz.riman.erp.batch.dto.salesOrderReturn;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SalesOrderReturnParamDto {
    @NotBlank(message = "CustomerReturnType must not be empty")
    @JsonProperty(value = "CustomerReturnType")
    private String CustomerReturnType;
    @NotBlank(message = "ReferenceSDDocument must not be empty")
    @JsonProperty(value = "ReferenceSDDocument")
	private String ReferenceSDDocument;
    @NotBlank(message = "SoldToParty must not be empty")
    @JsonProperty(value = "SoldToParty")
    private String SoldToParty;
    @NotBlank(message = "SDDocumentReason must not be empty")
    @JsonProperty(value = "SDDocumentReason")
    private String SDDocumentReason;
    @JsonProperty(value = "to_Item")
    private List<SalesOrderReturnItemDto> to_Item;
}
