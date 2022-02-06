package com.example.asynapi.controller;

import com.example.asynapi.model.Country;
import com.example.asynapi.model.DefaultResponse;
import com.example.asynapi.model.Summary;
import com.example.asynapi.service.Covid19Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CovidController {

    private final Covid19Service covid19Service;

    public CovidController(Covid19Service covid19Service) {
        this.covid19Service = covid19Service;
    }

    @GetMapping("/covid/summary")
    Summary getCovidSummary () {
        return covid19Service.getSummary();
//        return "Ok";
    }

    @GetMapping("/covid/")
    DefaultResponse getDefault () {
        return covid19Service.getDefault();
    }

    @GetMapping("/covid/countries")
    Country[] getCountries () {
        return covid19Service.getCountries();
    }
}
