package biz.riman.erp.batch.service.businessPartner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;

import biz.riman.erp.batch.dto.ResponseDto;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerAddressDto;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerBankDto;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerCustomerDto;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerDto;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerParamDto;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerSupplierDto;
import biz.riman.erp.batch.dto.businessPartner.BusinessPartnerTaxDto;
import biz.riman.erp.batch.dto.businessPartner.InterfaceBusinessPartnerDto;
import biz.riman.erp.batch.mapper.businessPartner.BusinessPartnerMapper;
import biz.riman.erp.batch.mapper.businessPartner.IfBusinessPartnerMapper;
import biz.riman.erp.batch.parameter.BusinessPartnerParameter;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BusinessPartnerUpdateService {
    @Autowired
    private BusinessPartnerMapper mapper;
    @Autowired
    private IfBusinessPartnerMapper ifMapper;

    private final WebClient localApiClient;
    
    @Autowired
    public BusinessPartnerUpdateService(@Qualifier("oAuth2WebClient") WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    /**
     * API request body param setting (webClient 비동기 처리를 위해 query 조회영역을 분리)
     * 
     * @param parameter
     * @return List<BusinessPartnerParamDto> 배치주기에 따른 BP수정 데이터 목록
     * @throws Exception
     */
    @Async
    public List<BusinessPartnerParamDto> setBodyParam(BusinessPartnerParameter parameter) throws Exception {
        List<BusinessPartnerParamDto> bodyParams = new ArrayList<BusinessPartnerParamDto>();
        
        // BP 데이터 조회
        List<BusinessPartnerDto> bpList = mapper.getBusinessPartners(parameter);
        if (CollectionUtils.isNotEmpty(bpList)) {
            for (BusinessPartnerDto bp : bpList) {
                BusinessPartnerParamDto bodyParam = new BusinessPartnerParamDto();
                BeanUtils.copyProperties(bp, bodyParam);
                
                // BP 주소 setting
                bodyParam.setTo_BusinessPartnerAddress(new ArrayList<BusinessPartnerAddressDto>(List.of(new BusinessPartnerAddressDto(bp))));
                // BP 역할 정보 setting
                bodyParam.setTo_BusinessPartnerRole(mapper.getBusinessPartnerRolses(parameter));
                // BP 고객사 정보 setting
                bodyParam.setTo_Customer(new BusinessPartnerCustomerDto(bp));
                // BP 세금 정보 setting
                bodyParam.setTo_BusinessPartnerTax(new ArrayList<BusinessPartnerTaxDto>(List.of(new BusinessPartnerTaxDto(bp))));
                // BP 은행 정보 setting
                List<BusinessPartnerBankDto> banks = new ArrayList<BusinessPartnerBankDto>();
                if (!StringUtil.isNullOrEmpty(bp.getBankAccount())) {
                    banks.add(new BusinessPartnerBankDto(bp));
                }
                bodyParam.setTo_BusinessPartnerBank(banks);
                // BP 공급처 정보 setting
                bodyParam.setTo_Supplier(new BusinessPartnerSupplierDto(bp));
                
                bodyParams.add(bodyParam);
            }
        }
        return bodyParams;
    }

    public String callBusinessPartnerUpdate(BusinessPartnerParameter parameter) throws Exception {
        String response = new String("");
        log.info("## BP 수정 I/F {} ~ {} ##", parameter.getStartDatetime(), parameter.getEndDatetime());
        log.info("");
        
        log.info("## Param setting ##");
        List<BusinessPartnerParamDto> bodyParams = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            CountDownLatch cdl = new CountDownLatch(bodyParams.size());
            
            for (BusinessPartnerParamDto bodyParam : bodyParams) {
                log.info("## api 요청 {} ##", bodyParam);
                localApiClient
                    .patch()
                    .uri("/http/cm/zst_bp_customer_update")
                    .accept(MediaType.APPLICATION_JSON)
                    .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("riman"))
                    .headers(headers -> {
                        headers.add("User-Agent", "Other");
                    })
                    .body(BodyInserters.fromValue(bodyParam))
                    .exchangeToMono(clientResponse -> clientResponse.toEntity(ResponseDto.class))
                    .doOnSuccess(clientResponse -> {
                        log.info("## clientResponse.getStatusCode() : {}", clientResponse.getStatusCode());
                        log.info("## clientResponse.getBody() : {}", clientResponse.getBody());
                        try {
                            log.info("## I/F 테이블 INSERT ##");
                            ifMapper.insertInterfaceBusinessPartner(new InterfaceBusinessPartnerDto(
                                    bodyParam.getBusinessPartner(),
                                    clientResponse.getBody().getResultStatus(),
                                    "U",
                                    clientResponse.getStatusCodeValue(),
                                    clientResponse.getBody().getMessage(),
                                    clientResponse.getBody().getSapMessageProcessingLogId()));
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    })
                    .subscribe(r -> {
                        log.info("## {} result : {}", r.getStatusCode(), r);
                        cdl.countDown();
                    });
            }
            
            cdl.await();
        } else {
            response = "전송할 BP 수정 데이터가 없습니다.";
        }
        
        return response;
    }

    public String callBusinessPartnerCreateDataTest(BusinessPartnerParameter parameter) throws Exception {
        String response = new String("");
        log.info("## BP 수정 I/F {} ~ {} ##", parameter.getStartDatetime(), parameter.getEndDatetime());
        log.info("");
        
        log.info("## Param setting ##");
        List<BusinessPartnerParamDto> bodyParams = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            for (BusinessPartnerParamDto bodyParam : bodyParams) {
                log.info("## bodyParam : {}", bodyParam);
            }
        } else {
            response = "전송할 BP 수정 데이터가 없습니다.";
        }
        
        return response;
    }
    
}
