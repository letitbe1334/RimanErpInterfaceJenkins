package biz.riman.erp.batch.dto.salesOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 배송비, 쿠폰
 */
@Data
public class PricingDto {
    @JsonProperty(value = "ConditionType")
	private String ConditionType;
    @JsonProperty(value = "ConditionRateValue")
	private String ConditionRateValue;
    @JsonProperty(value = "ConditionCurrency")
	private String ConditionCurrency;
    
    public PricingDto(String conditionType, String conditionRateValue, String conditionCurrency) {
        super();
        ConditionType = conditionType;
        ConditionRateValue = conditionRateValue;
        ConditionCurrency = conditionCurrency;
    }
    
}
