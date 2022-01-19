package com.aria.moviecatalogue.data.source.remote.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.aria.moviecatalogue.data.source.remote.response.StatusRespone.EMPTY;
import static com.aria.moviecatalogue.data.source.remote.response.StatusRespone.ERROR;
import static com.aria.moviecatalogue.data.source.remote.response.StatusRespone.SUCCESS;

public class ApiResponse<T> {

    @NonNull
    public final StatusRespone status;

    @Nullable
    public final String message;

    @Nullable
    public final T body;

    public ApiResponse(@NonNull StatusRespone status, @Nullable T body, @Nullable String message) {
        this.status = status;
        this.body = body;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(@Nullable T body) {
        return new ApiResponse<>(SUCCESS, body, null);
    }

    public static <T> ApiResponse<T> empty(String msg, @Nullable T body) {
        return new ApiResponse<>(EMPTY, body, msg);
    }

    public static <T> ApiResponse<T> error(String msg, @Nullable T body) {
        return new ApiResponse<>(ERROR, body, msg);
    }
}
