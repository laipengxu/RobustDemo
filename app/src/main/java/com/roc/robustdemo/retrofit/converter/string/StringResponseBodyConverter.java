package com.roc.robustdemo.retrofit.converter.string;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;

/**
 * Created by 耿 on 2016/9/6.
 */
public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}