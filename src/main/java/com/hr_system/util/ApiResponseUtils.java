package com.hr_system.util;

import org.springframework.data.domain.Page;

public class ApiResponseUtils {

    private ApiResponseUtils() {
    }

    public static <T> ApiResponse<T> createResponse(String status, String message, T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(status);
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> ApiResponse<T> createResponse(String status, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(status);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static <T> ApiResponse<T> createResponse(String status, String message, T data, Page<?> page) {
        ApiResponse<T> apiResponse = new ApiResponse<>();

        ApiResponse.Pagination pagination = new ApiResponse.Pagination();
        pagination.setCurrentPage(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalItems(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());

        apiResponse.setPagination(pagination);
        apiResponse.setStatus(status);
        apiResponse.setMessage(message);
        apiResponse.setData(data);

        return apiResponse;
    }

    public static <T> ApiResponse<T> createError(String status, int errorCode, String message, String detail) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(status);
        ApiResponse.ErrorDetails errorDetails = new ApiResponse.ErrorDetails();
        errorDetails.setMessage(message);
        errorDetails.setCode(String.valueOf(errorCode));
        errorDetails.setDetails(detail);
        apiResponse.setError(errorDetails);
        return apiResponse;
    }
}