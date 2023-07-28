package biz.riman.erp.batch.dto.salesOrderReturn;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SalesOrderReturnPaymentDto {
    @NotBlank(message = "ZSequence must not be empty")
    @JsonProperty(value = "ZSequence")
	private String ZSequence;
    @NotBlank(message = "YY1_ZPaymentMethod must not be empty")
    @JsonProperty(value = "YY1_ZPaymentMethod")
	private String YY1_ZPaymentMethod;
    @NotBlank(message = "ZAmount must not be empty")
    @JsonProperty(value = "ZAmount")
	private String ZAmount;
    @NotBlank(message = "ZCurrency must not be empty")
    @JsonProperty(value = "ZCurrency")
	private String ZCurrency;
}
