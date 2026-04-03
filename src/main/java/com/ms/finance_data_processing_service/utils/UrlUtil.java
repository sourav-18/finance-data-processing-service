package com.ms.finance_data_processing_service.utils;

public class UrlUtil {
    static final String BASE_URL = "/api";
    static final String VERSION = "/v1";

    public static final String AUTH_URL = BASE_URL + VERSION + "/auth";
    public static final String USER_URL = BASE_URL + VERSION + "/users";
    public static final String FINANCE_URL = BASE_URL + VERSION + "/finances";
    public static final String DASHBOARD_URL = BASE_URL + VERSION + "/dashboards";
}
