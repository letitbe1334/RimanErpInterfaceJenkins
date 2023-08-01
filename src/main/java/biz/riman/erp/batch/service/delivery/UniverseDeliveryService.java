package biz.riman.erp.batch.service.delivery;

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
import biz.riman.erp.batch.dto.delivery.DeliveryDocumentDto;
import biz.riman.erp.batch.dto.delivery.InterfaceDeliveryDto;
import biz.riman.erp.batch.mapper.delivery.IfDeliveryMapper;
import biz.riman.erp.batch.mapper.delivery.UniverseDeliveryMapper;
import biz.riman.erp.batch.parameter.DeliveryParameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UniverseDeliveryService {
    @Autowired
    private UniverseDeliveryMapper mapper;
    @Autowired
    private IfDeliveryMapper ifMapper;

    private final WebClient localApiClient;
    
    @Autowired
    public UniverseDeliveryService(@Qualifier("oAuth2WebClient") WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    /**
     * API request body param setting (webClient 비동기 처리를 위해 query 조회영역을 분리)
     * 
     * @param parameter
     * @return DeliveryDocumentItemDto 배치주기에 따른 출고 데이터 목록
     * @throws Exception
     */
    @Async
    public DeliveryDocumentDto setBodyParam(DeliveryParameter parameter) throws Exception {
        // 출고 데이터 조회
        return new DeliveryDocumentDto(mapper.getDeliveryDocumentItems(parameter));
    }

    public String callDelivery(DeliveryParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 출고 I/F {} ~ {} ##", parameter.getStartDate(), parameter.getEndDate());
        log.info("");
        
        log.info("## Param setting ##");
        DeliveryDocumentDto delivery = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(delivery.getTo_Item())) {
            CountDownLatch cdl = new CountDownLatch(1);

            log.info("## api 요청 {} ##", delivery);
            localApiClient
                .post()
                .uri("/http/sd/zct_do_pick")
                .accept(MediaType.APPLICATION_JSON)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("riman"))
                .headers(headers -> {
                    headers.add("User-Agent", "Other");
                })
                .body(BodyInserters.fromValue(delivery))
                .exchangeToMono(clientResponse -> clientResponse.toEntity(ResponseDto.class))
                .doOnSuccess(clientResponse -> {
                    log.info("## clientResponse.getStatusCode() : {}", clientResponse.getStatusCode());
                    log.info("## clientResponse.getBody() : {}", clientResponse.getBody());
                    try {
                        ifMapper.saveInterfaceDeliveries(delivery.getTo_Item(), new InterfaceDeliveryDto(
                                clientResponse.getBody().getResultStatus(),
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
            
            cdl.await();
        } else {
            response = "전송할 출고 데이터가 없습니다.";
        }
        
        return response;
    }

    public String callSalesOrderDataTest(DeliveryParameter parameter) throws Exception {
        String response = new String("");
        log.info("## 출고 I/F {} ~ {} ##", parameter.getStartDate(), parameter.getEndDate());
        log.info("");
        
        log.info("## Param setting ##");
        DeliveryDocumentDto delivery = setBodyParam(parameter);
        if (CollectionUtils.isNotEmpty(delivery.getTo_Item())) {
            log.info("## bodyParam : {}", delivery);
        } else {
            response = "전송할 출고 데이터가 없습니다.";
        }
        
        return response;
    }
    
}
