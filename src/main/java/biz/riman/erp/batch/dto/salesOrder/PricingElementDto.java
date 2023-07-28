package biz.riman.erp.batch.dto.salesOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PricingElementDto {
    @JsonProperty(value = "ConditionType")
	private String ConditionType;
    @JsonProperty(value = "ConditionRateValue")
	private String ConditionRateValue;
    @JsonProperty(value = "ConditionCurrency")
	private String ConditionCurrency;
	
	public PricingElementDto(String conditionType, String conditionRateValue, String conditionCurrency) {
		super();
		this.ConditionType = conditionType;
		this.ConditionRateValue = conditionRateValue;
		this.ConditionCurrency = conditionCurrency;
	}
}
