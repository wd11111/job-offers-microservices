package com.wd.offer.controller;

import lombok.AllArgsConstructor;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
public class ResponseBodyAssertMvc {

    private final MvcResult mvcResult;

    public static ResponseBodyAssertMvc then (MvcResult mvcResult) {
        return new ResponseBodyAssertMvc(mvcResult);
    }

    public ResponseBodyAssertMvc hasTheSameBodyAs(String expectedBody) throws UnsupportedEncodingException {
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedBody);
        return this;
    }

    public ResponseBodyAssertMvc containsBodyOf(String expectedBody) throws UnsupportedEncodingException {
        assertThat(mvcResult.getResponse().getContentAsString()).contains(expectedBody);
        return this;
    }
}
