package biz.riman.erp.batch.dto.salesOrderReturn;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SalesOrderReturnPricingDto {
    @NotBlank(message = "ConditionType must not be empty")
    @JsonProperty(value = "ConditionType")
	private String ConditionType;
    @NotBlank(message = "ConditionRateValue must not be empty")
    @JsonProperty(value = "ConditionRateValue")
	private String ConditionRateValue;
    @NotBlank(message = "ConditionCurrency must not be empty")
    @JsonProperty(value = "ConditionCurrency")
	private String ConditionCurrency;
}
