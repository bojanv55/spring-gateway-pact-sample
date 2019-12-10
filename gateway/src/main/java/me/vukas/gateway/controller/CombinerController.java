package me.vukas.gateway.controller;

import java.net.URI;
import me.vukas.gateway.dto.SiP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class CombinerController {

  @Value("${spring.cloud.gateway.routes[0].uri}") //TODO: fragile since using index 0
  private URI adderUri;
  @Value("${spring.cloud.gateway.routes[1].uri}")
  private URI multiplierUri;

  @GetMapping("/sip")
  public Mono<ResponseEntity<SiP>> proxy(Integer a, Integer b, ProxyExchange<String> proxy) {
    Mono<ResponseEntity<String>> sum = proxy.uri(adderUri.toString() + String.format("/add?a=%d&b=%d",a,b)).get();
    Mono<ResponseEntity<String>> product = proxy.uri(multiplierUri.toString() + String.format("/multiply?a=%d&b=%d",a,b)).get();

    return sum.zipWith(product)
        .map(t ->
            ResponseEntity
                .status(t.getT1().getStatusCode())
                .body(new SiP(Integer.parseInt(t.getT1().getBody()), Integer.parseInt(t.getT2().getBody())))
        );
  }

}
