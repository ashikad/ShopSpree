package com.shopspree.microservices.order.config;

import com.shopspree.microservices.order.client.InventoryClient;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;
    private final ObservationRegistry observationRegistry;

    @Bean
    public InventoryClient inventoryClient(){

        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .requestFactory(getClientRequestFactory())
                .observationRegistry(observationRegistry)
                .build();

        var resClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(resClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryClient.class);

    }

    //says ClientHttpRequestFactorySettings is deprecated
//    private ClientHttpRequestFactory getClientRequestFactory() {
//        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
//                .withConnectTimeout(Duration.ofSeconds(3))
//                .withReadTimeout(Duration.ofSeconds(3));
//        return ClientHttpRequestFactories.get(clientHttpRequestFactorySettings);
//    }



    private ClientHttpRequestFactory getClientRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);  // 3 seconds
        factory.setReadTimeout(3000);     // 3 seconds
        return factory;
    }
}
