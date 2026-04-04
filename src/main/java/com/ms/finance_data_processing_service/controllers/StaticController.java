package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.entites.Types.*;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlUtil.STATIC_URL)
@RequiredArgsConstructor
public class StaticController {

    @GetMapping("/user-status")
    public Object getUserStatus(){
        return UserStatusType.values();
    }

    @GetMapping("/user-roles")
    public Object geUserRoles(){
        return UserRoleType.values();
    }

    @GetMapping("/finance-status")
    public Object geFinanceStatus(){
        return FinanceStatusType.values();
    }

    @GetMapping("/finance-types")
    public Object geFinanceTypes(){
        return FinanceType.values();
    }

    @GetMapping("/finance-categories")
    public Object geFinanceCategories(){
        return FinanceCategoryType.values();
    }

}
