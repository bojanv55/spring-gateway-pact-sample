package me.vukas.gateway.controller;

import java.net.URI;
import me.vukas.gateway.dto.SiP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class CombinerController {

  @Value("${gateway.services.adder-uri}")
  private URI adderUri;
  @Value("${gateway.services.multiplier-uri}")
  private URI multiplierUri;

  @GetMapping("/sip") //http://localhost:7779/sip/?a=12&b=3
  public Mono<ResponseEntity<SiP>> proxy(Integer a, Integer b, ProxyExchange<String> proxy) {
    Mono<ResponseEntity<String>> sum = proxy.uri(
        UriComponentsBuilder.fromUri(adderUri)
            .path("add")
            .queryParam("a", a)
            .queryParam("b", b)
            .toUriString()
    ).get();
    Mono<ResponseEntity<String>> product = proxy.uri(
        UriComponentsBuilder.fromUri(multiplierUri)
            .path("multiply")
            .queryParam("a", a)
            .queryParam("b", b)
            .toUriString()
    ).get();

    return sum.zipWith(product)
        .map(t ->
            ResponseEntity
                .status(t.getT1().getStatusCode())
                .body(new SiP(
                    Integer.parseInt(t.mapT1(HttpEntity::getBody).getT1()),
                    Integer.parseInt(t.mapT2(HttpEntity::getBody).getT2())
                ))
        );
  }

}
