package com.ms.finance_data_processing_service.dtos.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ApiPageResponseDto<T> extends BaseApiResponseDto{
    private final List<T> data;
    private final int page;
    private final int size;
    private final int totalPageCount;
    private final long totalCount;

    public ApiPageResponseDto(boolean success, int statusCode, String message, List<T>  data, int page, int size, int totalPageCount, long totalCount) {
        super(success, statusCode, message);
        this.data=data;

        this.page = page;
        this.size = size;
        this.totalPageCount = totalPageCount;
        this.totalCount = totalCount;
    }
}
