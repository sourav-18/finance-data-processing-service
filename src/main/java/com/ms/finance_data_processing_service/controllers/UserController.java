package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.utils.UrlUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlUtil.USER_URL)
public class UserController {

    @PostMapping
    public String create(){
        System.out.println("create");
        return "ok";
    }
}
