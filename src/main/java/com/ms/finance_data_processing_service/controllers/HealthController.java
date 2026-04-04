package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.utils.UrlUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlUtil.HEALTH_URL)
public class HealthController {
    @GetMapping
    public String health(){
        return "ok";
    }
}
