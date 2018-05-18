package com.roc.robustdemo.retrofit.converter.bytes;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/6.
 */
public class ByteArrayResponseBodyConverter implements Converter<ResponseBody, byte[]> {
    @Override
    public byte[] convert(ResponseBody responseBody) throws IOException {
        try {
            return responseBody.bytes();
        } finally {
            responseBody.close();
        }
    }
}
