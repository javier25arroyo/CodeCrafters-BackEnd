package com.project.demo.logic.entity.http;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;


public class HttpResponse<T> {
    private  String message;
    private T data;

    private Meta meta;

    public HttpResponse(String message, Meta meta) {
        this.message = message;
        this.meta = meta;
    }

}
