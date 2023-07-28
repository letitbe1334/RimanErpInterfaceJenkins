package biz.riman.erp.batch.service.salesOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import biz.riman.erp.batch.mapper.salesOrder.IfSalesOrderMapper;
import biz.riman.erp.batch.mapper.salesOrder.UniverseSalesOrderMapper;
import biz.riman.erp.batch.parameter.SalesOrderParameter;
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
                bodyParam.setTo_Item(mapper.getSalesOrderItems(parameter));
                // 주문 배송비/쿠폰 정보 setting :: universe의 경우 쿠폰 없음
                List<PricingDto> pricing = new ArrayList<PricingDto>();
                pricing.add(new PricingDto(bodyParam.getDeliveryType(), bodyParam.getDeliveryRateValue(), "KRW"));
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
                    .accept(MediaType.APPLICATION_JSON) // , MediaType.APPLICATION_XML
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
                            ifMapper.insertInterfaceSalesOrder(new InterfaceSalseOrderDto(
                                    bodyParam.getPurchaseOrderByCustomer(),
                                    clientResponse.getBody().getReferenceNo(),
                                    clientResponse.getBody().getResultStatus(),
                                    clientResponse.getBody().getResultStatusDetail(),
                                    clientResponse.getStatusCodeValue(),
                                    clientResponse.getBody().getMessage()));
                            ifMapper.insertInterfaceSalesOrderItem(bodyParam.getTo_Item(),
                                    bodyParam.getPurchaseOrderByCustomer(),
                                    clientResponse.getBody().getReferenceNo());
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
    
    public String callSalesOrderTest(SalesOrderParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 판매오더 I/F {} ~ {} ##", parameter.getStartDatetime(), parameter.getEndDatetime());
        log.info("");
        
        log.info("## Param setting ##");
//        List<PricingElementDto> pricingElements = new ArrayList<PricingElementDto>();
//        PricingElementDto pricingElement = new PricingElementDto("YBHD", "2500", "KRW");
//        pricingElements.add(pricingElement); 
//        
//        List<ItemDto> items = new ArrayList<ItemDto>();
//        ItemDto item = new ItemDto("20", "QM001", "2", "PC");
//        items.add(item);
        
//        SalesOrderDto param = new SalesOrderDto("TA", "4310", "00", "10", "0043100001", "TEST20230707", 
//                "2023-06-21T00:00:00", "2023-06-21T00:00:00", "2023-06-21T00:00:00", 
//                pricingElements, items, null);

        int repeatCount = 30;
        CountDownLatch cdl = new CountDownLatch(repeatCount);
        
        for (int i = 0; i < repeatCount; i++) {
            // api 요청
            log.info("## api 요청 ##");
            localApiClient
            .post()
            .uri("/http/ZSB_SALES_ORDER_01_START_STD")
            .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
            .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("riman"))
            .headers(headers -> {
                headers.add("User-Agent", "Other");
            })
//            .body(BodyInserters.fromValue(param))
            .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
            .doOnSuccess(clientResponse -> {
                if (clientResponse.getStatusCode().is5xxServerError()) {
                    // 서버 에러
                    throw new RuntimeException(clientResponse.getBody());
                } else if (clientResponse.getStatusCode().is4xxClientError()) {
                    // client 에러
                    throw new RuntimeException(clientResponse.getBody());
                }
//                    clientResponse.getStatusCodeValue();
                
//                    if (HttpStatus.BAD_GATEWAY)
            })
//                .onErrorResume(error -> {
//                    return Mono.just(new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
//                })
            .subscribe(r -> {
                log.info("## {} result : {}", r.getStatusCode(), r);
//                    response = r.getBody().toString();
                cdl.countDown();
            });
        }
        
        cdl.await();
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
    

    public String callSalesOrderHardCoding(SalesOrderParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 판매오더 I/F {} ~ {} ##", parameter.getStartDatetime(), parameter.getEndDatetime());
        log.info("");
        
        log.info("## Param setting ##");

        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("SenderBusinessSystemName", "UNIVERSE");
        bodyMap.put("PurchaseOrderByCustomer", "1111");
        bodyMap.put("SalesOrderType", "TA");
        bodyMap.put("SalesOrganization", "4310");
        bodyMap.put("DistributionChannel", "10");
        bodyMap.put("SoldToParty", "43100001");
        bodyMap.put("RequestedDeliveryDate", "2023-07-09T00:00:00");
        bodyMap.put("CustomerPurchaseOrderDate", "2023-07-09T00:00:00");
        bodyMap.put("SalesOrderDate", "2023-07-09T00:00:00");
        bodyMap.put("ZSalesType", "ZST");
        bodyMap.put("ZBINC", "ZBinc");
        bodyMap.put("ZTINC", "ZTinc");
        bodyMap.put("ZRECOM", "ZRecom");
        bodyMap.put("ZGRECOM", "ZGrecom");
        List<Map<String, String>> to_Pricing = new ArrayList<Map<String, String>>();
        Map<String, String> pricingElemen = new HashMap<String, String>();
        pricingElemen.put("ConditionType", "YBHD");
        pricingElemen.put("ConditionRateValue", "5000");
        pricingElemen.put("ConditionCurrency", "KRW");
        to_Pricing.add(pricingElemen);
        bodyMap.put("to_Pricing", to_Pricing);
        List<Map<String, String>> to_Item = new ArrayList<Map<String, String>>();
        Map<String, String> item = new HashMap<String, String>();
        item.put("SalesOrderItem", "10");
        item.put("Material", "QM001");
        item.put("RequestedQuantity", "1");
        item.put("RequestedQuantityUnit", "PC");
        to_Item.add(item);
        item = new HashMap<String, String>();
        item.put("SalesOrderItem", "20");
        item.put("Material", "QM001");
        item.put("RequestedQuantity", "2");
        item.put("RequestedQuantityUnit", "PC");
        to_Item.add(item);
        bodyMap.put("to_Item", to_Item);
        List<Map<String, String>> to_ZPayment = new ArrayList<Map<String, String>>();
        Map<String, String> ZPayment = new HashMap<String, String>();
        ZPayment.put("ZSequence", "seq");
        ZPayment.put("ZPaymentMethod", "method");
        ZPayment.put("ZAmount", "amount");
        ZPayment.put("ZCurrency", "KRW");
        to_ZPayment.add(ZPayment);
        bodyMap.put("to_ZPayment", to_ZPayment);
        
        CountDownLatch cdl = new CountDownLatch(1);

        log.info("## api 요청 ##");
        localApiClient.post()
            .uri("/http/sd/zct_so_create")
            .accept(MediaType.APPLICATION_JSON) // , MediaType.APPLICATION_XML
            .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("riman"))
            .headers(headers -> {
                headers.add("User-Agent", "Other");
            })
            .body(BodyInserters.fromValue(bodyMap))
            .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
            .doOnSuccess(clientResponse -> {
                log.info("## clientResponse.getStatusCode() : {}", clientResponse.getStatusCode());
                log.info("## clientResponse.getBody() : {}", clientResponse.getBody());
            })
            .subscribe(r -> {
                log.info("## {} result : {}", r.getStatusCode(), r);
                cdl.countDown();
            });
        
        cdl.await();
        
        return response;
    }
}
