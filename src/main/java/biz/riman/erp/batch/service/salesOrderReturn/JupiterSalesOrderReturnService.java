package biz.riman.erp.batch.service.salesOrderReturn;

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
import biz.riman.erp.batch.dto.salesOrderReturn.InterfaceSalseOrderReturnDto;
import biz.riman.erp.batch.dto.salesOrderReturn.SalesOrderReturnDto;
import biz.riman.erp.batch.dto.salesOrderReturn.SalesOrderReturnPricingDto;
import biz.riman.erp.batch.mapper.salesOrderReturn.IfSalesOrderReturnMapper;
import biz.riman.erp.batch.mapper.salesOrderReturn.JupiterSalesOrderReturnMapper;
import biz.riman.erp.batch.parameter.SalesOrderReturnParameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JupiterSalesOrderReturnService {
    @Autowired
    private JupiterSalesOrderReturnMapper mapper;
    @Autowired
    private IfSalesOrderReturnMapper ifMapper;

    private final WebClient localApiClient;
    
    @Autowired
    public JupiterSalesOrderReturnService(@Qualifier("oAuth2WebClient") WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    /**
     * API request body param setting (webClient 비동기 처리를 위해 query 조회영역을 분리)
     * 
     * @param parameter
     * @return List<SalesOrderReturnDto> 배치주기에 따른 반품 데이터 목록
     * @throws Exception
     */
    @Async
    public List<SalesOrderReturnDto> setBodyParam(SalesOrderReturnParameter parameter) throws Exception {
        // 반품 데이터 조회
        List<SalesOrderReturnDto> bodyParams = mapper.getSalesOrderReturns(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            for (SalesOrderReturnDto bodyParam : bodyParams) {
                // 반품id
                parameter.setReturnOrderId(bodyParam.getReturnOrderByCustomer());
                
                // 반품 항목 정보 setting
                bodyParam.setTo_Item(mapper.getSalesOrderReturnItems(parameter));
                // 배송비 정보 setting :: universe의 경우 쿠폰 없음
                SalesOrderReturnPricingDto pricing = new SalesOrderReturnPricingDto();
                BeanUtils.copyProperties(bodyParam, pricing);
                bodyParam.setTo_Pricing(new ArrayList<SalesOrderReturnPricingDto>(List.of(pricing)));
                // 반품 결제 정보 setting
                bodyParam.setTo_ZPayment(mapper.getSalesOrderReturnPayments(parameter));
            }
        }
        return bodyParams;
    }

    public String callSalesOrderReturn(SalesOrderReturnParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 반품 I/F {} ~ {} ##", parameter.getStartDate(), parameter.getEndDate());
        log.info("");
        
        log.info("## Param setting ##");
        List<SalesOrderReturnDto> bodyParams = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            CountDownLatch cdl = new CountDownLatch(bodyParams.size());
            
            for (SalesOrderReturnDto bodyParam : bodyParams) {
                // api 요청
                log.info("## api 요청 {} ##", bodyParam);
                localApiClient
                        .post()
                        .uri("/http/sd/zct_customer_return")
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
                                ifMapper.insertInterfaceSalesOrderReturn(new InterfaceSalseOrderReturnDto(
                                        bodyParam.getPurchaseOrderByCustomer(),
                                        bodyParam.getReturnOrderByCustomer(),
                                        bodyParam.getReferenceSDDocument(),
                                        clientResponse.getBody().getReferenceNo(),
                                        clientResponse.getBody().getResultStatus(),
                                        clientResponse.getStatusCodeValue(),
                                        clientResponse.getBody().getMessage()));
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
            response = "전송할 반품 데이터가 없습니다.";
        }
        
        
        return response;
    }
}
