package biz.riman.erp.batch.service.salesOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
import biz.riman.erp.batch.dto.salesOrder.InterfaceSalseOrderDto;
import biz.riman.erp.batch.dto.salesOrder.PricingDto;
import biz.riman.erp.batch.dto.salesOrder.SalesOrderDto;
import biz.riman.erp.batch.dto.salesOrder.SalesOrderItemDto;
import biz.riman.erp.batch.mapper.salesOrder.IfSalesOrderMapper;
import biz.riman.erp.batch.mapper.salesOrder.UniverseSalesOrderMapper;
import biz.riman.erp.batch.parameter.SalesOrderParameter;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UniverseSalesOrderService {
    @Autowired
    private UniverseSalesOrderMapper mapper;
    @Autowired
    private IfSalesOrderMapper ifMapper;

    private final WebClient localApiClient;
    
    @Autowired
    public UniverseSalesOrderService(@Qualifier("oAuth2WebClient") WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    /**
     * API request body param setting (webClient 비동기 처리를 위해 query 조회영역을 분리)
     * 
     * @param parameter
     * @return List<SalesOrderParamDto> 배치주기에 따른 주문생성 데이터 목록
     * @throws Exception
     */
    @Async
    public List<SalesOrderDto> setBodyParam(SalesOrderParameter parameter) throws Exception {
        // 주문 데이터 조회
        List<SalesOrderDto> bodyParams = mapper.getSalesOrders(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            for (SalesOrderDto bodyParam : bodyParams) {
                // universe 주문id / 결제id
                parameter.setSalesOrderId(bodyParam.getSalesOrderId());
                parameter.setPaymentId(bodyParam.getPaymentId());
                
                // 주문 항목 정보 setting
                List<SalesOrderItemDto> items = mapper.getSalesOrderItems(parameter);
                /**
                 * 주문상품 목록 하단에 배송비 정보 삽입
                 * 배송비가 없는 경우 보내지 않음.
                 * */
                if (!StringUtil.isNullOrEmpty(bodyParam.getDeliveryType())) {
                	items.add(new SalesOrderItemDto(
                			String.valueOf((items.size() + 1) * 10), 
                			"990001",
                			"1",
                			"EA",
                			"Y"));
                }
                bodyParam.setTo_Item(items);
                // 주문 배송비/쿠폰 정보 setting :: universe의 경우 쿠폰 없음
                List<PricingDto> pricing = new ArrayList<PricingDto>();
//                pricing.add(new PricingDto(bodyParam.getDeliveryType(), bodyParam.getDeliveryRateValue(), "KRW"));
                bodyParam.setTo_Pricing(pricing);
                // 주문 결제 정보 setting
                bodyParam.setTo_ZPayment(mapper.getSalesOrderPayments(parameter));
            }
        }
        
        return bodyParams;
    }

    public String callSalesOrder(SalesOrderParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 판매오더 I/F {} ~ {} ##", parameter.getStartDatetime(), parameter.getEndDatetime());
        log.info("");
        
        log.info("## Param setting ##");
        List<SalesOrderDto> bodyParams = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            CountDownLatch cdl = new CountDownLatch(bodyParams.size());
            for (SalesOrderDto bodyParam : bodyParams) {
                log.info("## api 요청 {} ##", bodyParam);
                localApiClient
                    .post()
                    .uri("/http/sd/zct_so_create")
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
                            ifMapper.saveInterfaceSalesOrder(new InterfaceSalseOrderDto(
                                    bodyParam.getPurchaseOrderByCustomer(),
                                    clientResponse.getBody().getReferenceNo(),
                                    clientResponse.getBody().getResultStatus(),
                                    clientResponse.getBody().getResultStatusDetail(),
                                    clientResponse.getStatusCodeValue(),
                                    clientResponse.getBody().getMessage(),
                                    clientResponse.getBody().getSapMessageProcessingLogId()));
                            ifMapper.saveInterfaceSalesOrderItem(bodyParam.getTo_Item(),
                                    bodyParam.getPurchaseOrderByCustomer(),
                                    clientResponse.getBody().getReferenceNo(),
                                    clientResponse.getBody().getResultStatus(),
                                    clientResponse.getStatusCodeValue(),
                                    clientResponse.getBody().getMessage());
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
            response = "전송할 판매 오더 데이터가 없습니다.";
        }
        
        return response;
    }

    public String callSalesOrderDataTest(SalesOrderParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 판매오더 I/F {} ~ {} ##", parameter.getStartDatetime(), parameter.getEndDatetime());
        log.info("");
        
        log.info("## Param setting ##");
        List<SalesOrderDto> bodyParams = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            for (SalesOrderDto bodyParam : bodyParams) {
                log.info("## bodyParam : {}", bodyParam);
            }
        } else {
            response = "전송할 판매 오더 데이터가 없습니다.";
        }
        
        return response;
    }
    
}
