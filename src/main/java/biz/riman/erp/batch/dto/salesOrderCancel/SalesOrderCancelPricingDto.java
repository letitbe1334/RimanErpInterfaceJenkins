package biz.riman.erp.batch.dto.salesOrderCancel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 쿠폰
 */
@Data
public class SalesOrderCancelPricingDto {
    @JsonProperty(value = "ConditionType")
	private String ConditionType;
    @JsonProperty(value = "ConditionRateValue")
	private String ConditionRateValue;
    @JsonProperty(value = "ConditionCurrency")
	private String ConditionCurrency;
}
