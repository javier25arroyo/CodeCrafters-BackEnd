package com.project.demo.logic.entity.http;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    private String method;
    private String url;

    private int totalPages;

    private long totalElements;

    private int pageNumber;

    private int pageSize;

    public Meta(String method, String url) {
        this.method = method;
        this.url = url;
    }

    
}
