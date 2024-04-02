package pl.lotto.infrastructure.numbergenerator.http;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.lotto.domain.numbergenerator.RandomNumberGenerable;
import pl.lotto.domain.numbergenerator.SixRandomNumbersDto;

@AllArgsConstructor
public class RandomNumberGeneratorRestTemplate implements RandomNumberGenerable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;
    @Override
    public SixRandomNumbersDto generateSixRandomNumbers( int count, int lowerBand, int upperBand) {

        String urlForService = getUrlFromService("/api/v1.0/random");

        HttpHeaders headers = new HttpHeaders();

        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .queryParam("min", lowerBand)
                .queryParam("max", upperBand)
                .queryParam("count", count)
                .toUriString();

        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        List<Integer> numbers = response.getBody();
        System.out.println(numbers);
        return SixRandomNumbersDto.builder().numbers(numbers.stream().collect(Collectors.toSet())).build();
    }

    private String getUrlFromService(String service){
        return uri + ":" + port + service;

    }

}