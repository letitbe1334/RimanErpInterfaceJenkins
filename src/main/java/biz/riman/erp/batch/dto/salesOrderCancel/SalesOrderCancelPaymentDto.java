package biz.riman.erp.batch.dto.salesOrderCancel;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 결제
 */
@Data
public class SalesOrderCancelPaymentDto {
    @NotBlank(message = "ZSequence must not be empty")
    @JsonProperty(value = "ZSequence")
	private String ZSequence;
    @NotBlank(message = "ZPaymentMethod must not be empty")
    @JsonProperty(value = "ZPaymentMethod")
	private String ZPaymentMethod;
    @NotBlank(message = "ZAmount must not be empty")
    @JsonProperty(value = "ZAmount")
	private String ZAmount;
    @NotBlank(message = "ZCurrency must not be empty")
    @JsonProperty(value = "ZCurrency")
	private String ZCurrency;
}
