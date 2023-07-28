package biz.riman.erp.batch.dto.salesOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AddressDto {
    @JsonProperty(value = "CityName")
	private String CityName;
    @JsonProperty(value = "DistrictName")
	private String DistrictName;
    @JsonProperty(value = "StreetName")
    private String StreetName;
//    @JsonProperty(value = "PostalCode")
//    private String PostalCode;
//    @JsonProperty(value = "HouseNumber")
//	private String HouseNumber;
	
	public AddressDto(String cityName, String districtName, String streetName) {
		super();
		this.CityName = cityName;
		this.DistrictName = districtName;
		this.StreetName = streetName;
	}
}
