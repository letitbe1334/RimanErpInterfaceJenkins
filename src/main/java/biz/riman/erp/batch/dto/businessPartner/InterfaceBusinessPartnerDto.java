package biz.riman.erp.batch.dto.businessPartner;

import biz.riman.erp.batch.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InterfaceBusinessPartnerDto extends BaseDto {
	// BP id
	private String businessPartner;
	// 성공여부 (Y, N, null)
    private String resultStatus;
    // 상세 내용(코드)
    private String saveFlag;
    // http 상태코드
    private int httpStatus;
    // 에러
    private String exceptions;
    
    public InterfaceBusinessPartnerDto(String businessPartner) {
        super();
        this.businessPartner = businessPartner;
    }

    public InterfaceBusinessPartnerDto(String businessPartner, String resultStatus,
            String saveFlag, int httpStatus, String exceptions) {
        super();
        this.businessPartner = businessPartner;
        this.resultStatus = resultStatus;
        this.saveFlag = saveFlag;
        this.httpStatus = httpStatus;
        this.exceptions = exceptions;
    }
    
}
