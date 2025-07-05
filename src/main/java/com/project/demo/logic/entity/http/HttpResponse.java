package com.project.demo.logic.entity.http;



import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse<T> {
    private  String message;
    private T data;

    private Meta meta;

    public HttpResponse(String message, Meta meta) {
        this.message = message;
        this.meta = meta;
    }

}
