package biz.riman.erp.batch.dto.salesOrder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PartnerDto {
    @JsonProperty(value = "PartnerFunction")
	private String PartnerFunction;
    @JsonProperty(value = "Customer")
	private String Customer;
    @JsonProperty(value = "AddressID")
	private String AddressID;
    @JsonProperty(value = "to_Address")
	private List<AddressDto> to_Address;
	
	public PartnerDto(String partnerFunction, String customer, String addressID, List<AddressDto> to_Address) {
		super();
		this.PartnerFunction = partnerFunction;
		this.Customer = customer;
		this.AddressID = addressID;
		this.to_Address = to_Address;
	}
}
