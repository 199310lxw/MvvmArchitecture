package com.example.lib_net.interceptor;

import com.example.lib_net.BuildConfig;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author: Administrator
 * time: 2022/8/9
 * desc: 自定义header拦截器
 */
public class HeaderInterceptor implements Interceptor {

    private final HashMap<String, String> map;

    public HeaderInterceptor() {
        map = new HashMap<>();
    }

    void addHeader(String key, String value) {
        map.put(key, value);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        request = builder.build();

        if (BuildConfig.DEBUG) {
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(request);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            assert response.body() != null;
            MediaType mediaType = Objects.requireNonNull(response.body()).contentType();
            String content = Objects.requireNonNull(response.body()).string();
            Logger.i("request = " + request + ", headers = " + request.headers());
            Logger.i("Response:" + content);
            Logger.i("Request End:" + duration + "毫秒----------");
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
        return chain.proceed(request);
    }
}
