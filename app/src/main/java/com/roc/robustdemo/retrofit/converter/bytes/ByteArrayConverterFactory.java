package com.roc.robustdemo.retrofit.converter.bytes;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/6/6.
 */
public class ByteArrayConverterFactory extends Converter.Factory {

    private ByteArrayConverterFactory(){}

    public static ByteArrayConverterFactory create(){
        return new ByteArrayConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ByteArrayResponseBodyConverter();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
