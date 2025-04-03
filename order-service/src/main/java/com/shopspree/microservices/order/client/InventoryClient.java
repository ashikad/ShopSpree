package com.shopspree.microservices.order.client;

//import org.springframework.cloud.openfeign.FeignClient;
import groovy.util.logging.Slf4j;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Changed to use RestClient instead of WebClient
//@FeignClient(value = "inventory", url="${inventory.url}"
@Slf4j
public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);


    //@RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean fallbackMethod(String code, Integer quantity, Throwable throwable){
        log.info("Cannot get inventory for skucode {}, failure reason: {}", code, throwable.getMessage());
        return false;
    }

}
