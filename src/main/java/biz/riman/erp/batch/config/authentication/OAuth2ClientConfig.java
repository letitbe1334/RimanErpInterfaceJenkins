package biz.riman.erp.batch.config.authentication;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Slf4j
@Configuration
public class OAuth2ClientConfig {
    @Value("${spring.security.oauth2.client.endpoint}") 
    private String endPoint;
    
    @Value("${spring.security.oauth2.client.provider.riman.authorization-uri}") 
    private String authorizationUrl;
    
    @Value("${spring.security.oauth2.client.provider.riman.token-uri}") 
    private String tokenUri;
    
    @Value("${spring.security.oauth2.client.registration.riman.client-id}") 
    private String clientId;
    
    @Value("${spring.security.oauth2.client.registration.riman.client-secret}") 
    private String clientSecret;
    
    @Value("${spring.security.oauth2.client.registration.riman.authorization-grant-type}") 
    private String authorizationGrantType;
    
    @Value("${spring.security.oauth2.client.registration.riman.client-authentication-method}") 
    private String clientAuthenticationMethod;

    @Bean(name = "clientRegistrations")
    ReactiveClientRegistrationRepository clientRegistrations() {
        log.info("## ClientRegistration 세팅 ##");
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("riman")
                .authorizationUri(authorizationUrl)
                .tokenUri(tokenUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .clientAuthenticationMethod(new ClientAuthenticationMethod(clientAuthenticationMethod))
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

    @Bean(name = "oAuth2WebClient")
    WebClient oAuth2WebClient(@Qualifier("clientRegistrations") ReactiveClientRegistrationRepository clientRegistrations) {
        log.info("## oAuth2WebClient WebClient 세팅 ##");
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("riman");
        oauth.setDefaultOAuth2AuthorizedClient(true);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
                 .build();
        
        ConnectionProvider connectionProvider = ConnectionProvider.builder("myConnectionPool")
                .maxConnections(1000)
                .pendingAcquireMaxCount(-1)
                .build();
        ReactorClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(HttpClient.create(connectionProvider));
        
        return WebClient.builder()
                .baseUrl(endPoint)
                .filter(oauth)
                .clientConnector(clientHttpConnector)
                .exchangeStrategies(exchangeStrategies)
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(configurer -> 
                                        configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder())
                                )
                                .build()
                )
                .build();
    }
}
