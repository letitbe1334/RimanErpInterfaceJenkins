package biz.riman.erp.batch.dto.salesOrderReturn;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SalesOrderReturnItemDto {
    @NotBlank(message = "ReferenceSDDocument must not be empty")
    @JsonProperty(value = "ReferenceSDDocument")
	private String ReferenceSDDocument;
    @NotBlank(message = "ReferenceSDDocumentItem must not be empty")
    @JsonProperty(value = "ReferenceSDDocumentItem")
	private String ReferenceSDDocumentItem;
    @NotBlank(message = "RequestedQuantity must not be empty")
    @JsonProperty(value = "RequestedQuantity")
	private String RequestedQuantity;
    @NotBlank(message = "ReturnReason must not be empty")
    @JsonProperty(value = "ReturnReason")
	private String ReturnReason;
    @NotBlank(message = "ReturnsRefundExtent must not be empty")
    @JsonProperty(value = "ReturnsRefundExtent")
    private String ReturnsRefundExtent;
    @NotBlank(message = "ReturnsRefundProcgMode must not be empty")
    @JsonProperty(value = "ReturnsRefundProcgMode")
    private String ReturnsRefundProcgMode;
    @NotBlank(message = "CustRetItmFollowUpActivity must not be empty")
    @JsonProperty(value = "CustRetItmFollowUpActivity")
    private String CustRetItmFollowUpActivity;
}
