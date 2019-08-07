package com.paladin.credit.service.xyb.response;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <>
 *
 * @author Huangguochen
 * @create 2019/8/5 16:30
 */
public class XYBHttpMessageConverterExtractor<T> implements ResponseExtractor<ResponseEntity<T>> {

    @Nullable
    private final HttpMessageConverterExtractor<T> delegate;

    public XYBHttpMessageConverterExtractor(@Nullable Type responseType, List<HttpMessageConverter<?>> converter) {
        if (responseType != null && Void.class != responseType) {
            this.delegate = new HttpMessageConverterExtractor<>(responseType, converter);
        }
        else {
            this.delegate = null;
        }
    }

    @Override
    public ResponseEntity<T> extractData(ClientHttpResponse response) throws IOException {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        if (this.delegate != null) {
            T body = this.delegate.extractData(response);
            return ResponseEntity.status(response.getRawStatusCode()).headers(response.getHeaders()).body(body);
        }
        else {
            return ResponseEntity.status(response.getRawStatusCode()).headers(response.getHeaders()).build();
        }
    }
}
