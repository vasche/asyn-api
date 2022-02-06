package com.example.asynapi.service;

import com.example.asynapi.model.Country;
import com.example.asynapi.model.DefaultResponse;
import com.example.asynapi.model.Summary;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class Covid19Service {

    @Value("${api.baseurl}")
    private String baseUrl;

    @Value("${api.summary_path}")
    private String summaryPath;

    @Value("${api.all_path}")
    private String allPath;

    @Value("${api.default_path}")
    private String defaultPath;


    @Value("${api.countries_path}")
    private String countriesPath;

    @Value("${api.user}")
    private String apiUser;

    @Value("${api.password}")
    private String apiPassword;

    @Value("${api.access_token}")
    private String token;

    final private RestTemplate restTemplate;


    public Covid19Service(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Summary getSummary() {
        log.info("get covid summary");
        ResponseEntity<Summary> responseEntity = restTemplate.exchange
                (URI.create(baseUrl + summaryPath), HttpMethod.GET, new HttpEntity<>(getHeaders()), Summary.class);
        return responseEntity.getBody();
//        return restTemplate.getForObject(URI.create(baseUrl + summaryPath), Summary.class);
    }

    private HttpHeaders getHeaders() {
        return new HttpHeaders() {{
            String auth = apiUser + ":" + apiPassword;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
            set("X-Access-Token", token);
        }};
    }

    public DefaultResponse getDefault() {
        log.info("get Default");
        try {
            ResponseEntity<DefaultResponse> responseEntity = restTemplate.exchange
                    (URI.create(baseUrl + defaultPath), HttpMethod.GET, new HttpEntity<>(getHeaders()), DefaultResponse.class);
            return responseEntity.getBody();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        return null;
    }

    public Country[] getCountries() {
        log.info("get Countries");
        try {
            ResponseEntity<Country[]> responseEntity = restTemplate.exchange
                    (URI.create(baseUrl + countriesPath), HttpMethod.GET, new HttpEntity<>(getHeaders()), Country[].class);
            return responseEntity.getBody();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        return null;
    }
}
