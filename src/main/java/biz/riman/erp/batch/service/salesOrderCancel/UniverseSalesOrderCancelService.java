package biz.riman.erp.batch.service.salesOrderCancel;

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
import biz.riman.erp.batch.dto.salesOrderCancel.InterfaceSalseOrderCancelDto;
import biz.riman.erp.batch.dto.salesOrderCancel.SalesOrderCancelDto;
import biz.riman.erp.batch.dto.salesOrderCancel.SalesOrderCancelPricingDto;
import biz.riman.erp.batch.mapper.salesOrderCancel.IfSalesOrderCancelMapper;
import biz.riman.erp.batch.mapper.salesOrderCancel.UniverseSalesOrderCancelMapper;
import biz.riman.erp.batch.parameter.SalesOrderCancelParameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UniverseSalesOrderCancelService {
    @Autowired
    private UniverseSalesOrderCancelMapper mapper;
    @Autowired
    private IfSalesOrderCancelMapper ifMapper;

    private final WebClient localApiClient;
//    private final WebClient sapApiClient;
//
//    String token = "";
//    MultiValueMap<String, String> myCookies = new LinkedMultiValueMap<String, String>();
//
//    private String host = "https://my405068.s4hana.cloud.sap";
//    private String uri = "/sap/opu/odata/sap/ZAPI_SALES_CANCEL/salesCancel";
//    private String userName = "Z_SD_TEST_USER";
//    private String password = "VqiBfhQaQNN}MKyQljnvUocmAzvGw3tNroTaPCiW";
    
    
    @Autowired
    public UniverseSalesOrderCancelService(@Qualifier("oAuth2WebClient") WebClient localApiClient) {
        this.localApiClient = localApiClient;

//        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
//                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
//                 .build();
//        this.sapApiClient  = WebClient
//            .builder()
//            .baseUrl(host)
//            .exchangeStrategies(exchangeStrategies)
//            .build();
    }

    /**
     * API request body param setting (webClient 비동기 처리를 위해 query 조회영역을 분리)
     * 
     * @param parameter
     * @return List<SalesOrderCancelParamDto> 배치주기에 따른 주문취소 데이터 목록
     * @throws Exception
     */
    @Async
    public List<SalesOrderCancelDto> setBodyParam(SalesOrderCancelParameter parameter) throws Exception {
        // 주문취소 데이터 조회
        List<SalesOrderCancelDto> bodyParams = mapper.getSalesOrderCancels(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            for (SalesOrderCancelDto bodyParam : bodyParams) {
                // 주문id
                parameter.setSalesOrderId(bodyParam.getSalesOrderId());
                parameter.setPaymentId(bodyParam.getPaymentId());

                // 주문취소 쿠폰 정보 setting
                bodyParam.setTo_Pricing(new ArrayList<SalesOrderCancelPricingDto>());

                // 주문취소 상품 정보 setting
                bodyParam.setTo_Item(mapper.getSalesOrderCancelItems(parameter));
                
                // 주문취소 결제 정보 setting
                bodyParam.setTo_ZPayment(mapper.getSalesOrderCancelPayments(parameter));
            }
        }
        return bodyParams;
    }

    public String callSalesOrderCancel(SalesOrderCancelParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 주문취소 I/F {} ~ {} ##", parameter.getStartDate(), parameter.getEndDate());
        log.info("");
        
        log.info("## Param setting ##");
        List<SalesOrderCancelDto> bodyParams = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            CountDownLatch cdl = new CountDownLatch(bodyParams.size());

            for (SalesOrderCancelDto bodyParam : bodyParams) {
                // api 요청
                log.info("## api 요청 {} ##", bodyParam);
                localApiClient
                        .post()
                        .uri("/http/sd/zct_so_cancel")
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
                                ifMapper.insertInterfaceSalesOrderCancel(new InterfaceSalseOrderCancelDto(
                                        bodyParam.getPurchaseOrderByCustomer(),
                                        bodyParam.getSalesOrder(),
                                        clientResponse.getBody().getResultStatus(),
                                        clientResponse.getStatusCodeValue(),
                                        clientResponse.getBody().getMessage()));
                            } catch (Exception e) {
                                // I/F Table update시 예외발생시 어떻게 처리할 것인지?
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
            response = "전송할 주문취소 데이터가 없습니다.";
        }
        
        
        return response;
    }

    public String callSalesOrderCancelDataTest(SalesOrderCancelParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 주문취소 I/F {} ~ {} ##", parameter.getStartDate(), parameter.getEndDate());
        log.info("");
        
        log.info("## Param setting ##");
        List<SalesOrderCancelDto> bodyParams = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(bodyParams)) {
            for (SalesOrderCancelDto bodyParam : bodyParams) {
                log.info("## bodyParam : {}", bodyParam);
            }
        } else {
            response = "전송할 주문취소 데이터가 없습니다.";
        }
        
        return response;
    }
    
//    public String callSalesOrderCancelSAP(SalesOrderCancelParameter parameter) throws Exception {
//        String response = new String("");
//        log.info("## 주문취소 I/F {} ~ {} ##", parameter.getStartDate(), parameter.getEndDate());
//        log.info("");
//
//        getToken();
//        
//        log.info("## Param setting ##");
//        
//        int repeatCount = 1;
//        CountDownLatch cdl = new CountDownLatch(repeatCount);
//        
//        for (int i = 0; i < repeatCount; i++) {
//            SalesOrderCancelDto param = new SalesOrderCancelDto(("" + (36 + i)), Arrays.asList(new SalesOrderCancelItemDto("10", "70"), new SalesOrderCancelItemDto("20", "70")));
//            log.info("## param : {}", param);
//            
//            // api 요청
//            log.info("## api 요청 ##");
//            sapApiClient
//                .post()
//                .uri(uri)
//                .accept(MediaType.APPLICATION_JSON)
//                .headers(headers -> {
//                    headers.add("x-csrf-token", token);
//                    headers.setBasicAuth(userName, password);
//                })
//                .cookies(cookies -> cookies.addAll(myCookies))
//                .body(BodyInserters.fromValue(param))
//                .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class))
//                .doOnSuccess(clientResponse -> {
//                    log.info("## clientResponse.getStatusCode() : {}", clientResponse.getStatusCode());
//                    log.info("## clientResponse.getBody() : {}", clientResponse.getBody());
////                    if (clientResponse.getStatusCode().is5xxServerError()) {
////                        // 서버 에러
////                        throw new RuntimeException(clientResponse.getBody());
////                    } else if (clientResponse.getStatusCode().is4xxClientError()) {
////                        // client 에러
////                        throw new RuntimeException(clientResponse.getBody());
////                    }
//                })
//                .subscribe(r -> {
//                    log.info("## {} result : {}", r.getStatusCode(), r);
//                    cdl.countDown();
//                });
//        }
//        
//        cdl.await();
//        return response;
//    }
//    
//    public void getToken() {
//        // token 인증
//        log.info("token 인증");
//        sapApiClient
//                .get()
//                .uri(uri)
//                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
//                .headers(headers -> {
//                    headers.add("x-csrf-token", "fetch");
//                    headers.setBasicAuth(userName, password);
//                })
//                .exchangeToMono(res -> {
//                    if (res.statusCode().equals(HttpStatus.OK)) {
//                      for (String key: res.cookies().keySet()) {
//                          myCookies.put(key, Arrays.asList(res.cookies().get(key).get(0).getValue()));
//                      }
//                      List<String> tokens = res.headers().asHttpHeaders().get("x-csrf-token");
//                        if (tokens != null && tokens.size() > 0) {
//                            token = tokens.get(0);
//                            return res.bodyToMono(Map.class);
//                        } else {
//                            return res.createException().flatMap(Mono::error);
//                        }
//                    } else {
//                        return res.createException().flatMap(Mono::error);
//                    }
//                })
//                .block();
//        
//        log.info("### TOKEN : {}", token);
//        log.info("### myCookies : {}", myCookies);
//    }
}
