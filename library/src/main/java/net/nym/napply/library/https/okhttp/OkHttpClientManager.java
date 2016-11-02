/*
 * Copyright (c) 2016  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.napply.library.https.okhttp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import net.nym.napply.library.BuildConfig;
import net.nym.napply.library.cookie.CookieJarImpl;
import net.nym.napply.library.cookie.store.PersistentCookieStore;
import net.nym.napply.library.https.okhttp.callback.OkHttpCallback;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author niyueming
 * @date 2016-08-10
 * @time 14:24
 */
public class OkHttpClientManager {
    private volatile static OkHttpClient mOkHttpClient;
    private final static long MAX_CACHE_SIZE = 50 * 1024 * 1024;
    private final static int CONNECT_TIMEOUT = 60;  //秒
    private final static int READ_TIMEOUT = 60;  //秒
    private final static int WRITE_TIMEOUT = 60;  //秒
    private Request.Builder mBuilder;
    private static final int REQUEST_ID = -1;
    private METHOD mMethod = METHOD.GET;
    protected String url;
    protected Map<String, String> params;
    private List<FileInput> files ;
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
    private Platform mPlatform = Platform.get();

    private OkHttpClientManager(Context context) {
        if (mOkHttpClient == null){
            initHttpClient(context);
        }
    }

    public static OkHttpClientManager newInstance(Context context) {
        return new OkHttpClientManager(context);
    }

    private void initHttpClient(Context context){
//        //        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null,null,null);
//        mOkHttpClient = new OkHttpClient().newBuilder()
//                .addInterceptor(new LoggerInterceptor("NApply", true))
//                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))   //cookie缓存
//                .cache(new Cache(context.getCacheDir(), MAX_CACHE_SIZE))
////                .certificatePinner(CertificatePinner.DEFAULT) //证书锁定
//                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
////                .connectionSpecs()    //TLS ;代理？
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
////                .dispatcher(new Dispatcher())
////                .followRedirects(true)    //重定向，默认true
////                .followSslRedirects(true) //follow redirects from HTTPS to HTTP and from HTTP to HTTPS.默认true
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
////                .retryOnConnectionFailure()
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
////                .sslSocketFactory(sslParams.sSLSocketFactory) //自定义https证书
//                .build();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
//                .addInterceptor(new LoggerInterceptor("NApply", true))
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))   //cookie缓存
                .cache(new Cache(context.getCacheDir(), MAX_CACHE_SIZE))
//                .certificatePinner(CertificatePinner.DEFAULT) //证书锁定
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
//                .connectionSpecs()    //TLS ;代理？
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .dispatcher(new Dispatcher())
//                .followRedirects(true)    //重定向，默认true
//                .followSslRedirects(true) //follow redirects from HTTPS to HTTP and from HTTP to HTTPS.默认true
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .retryOnConnectionFailure()
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .sslSocketFactory(sslParams.sSLSocketFactory) //自定义https证书
        ;
        if (BuildConfig.DEBUG){
            builder.addInterceptor(new LoggerInterceptor("NApply", true));
        }
        mOkHttpClient = builder.build();

    }

    public OkHttpClientManager newBuilder(){
        mBuilder = new Request.Builder();
        mMethod = METHOD.GET;
        params = new LinkedHashMap<>();
        files = new ArrayList<>();
        url = null;
        return this;
    }

    public OkHttpClientManager method(METHOD method){
        mMethod = method;
        return this;
    }

    public OkHttpClientManager addHeader(@NonNull String name,@NonNull String value){
        checkBuilder();
        mBuilder.addHeader(name, value);
        return this;
    }

    public OkHttpClientManager addHeader(@NonNull Headers headers){
        checkBuilder();
        mBuilder.headers(headers);
        return this;
    }

    public OkHttpClientManager url(@NonNull String url){
        this.url = url;
        return this;
    }

    public OkHttpClientManager tag(@NonNull Object tag){
        checkBuilder();
        mBuilder.tag(tag);
        return this;
    }

    public OkHttpClientManager params(@NonNull Map<String, String> params){
        if (this.params == null){
            this.params = new LinkedHashMap<String, String>();
        }
        this.params.clear();
        this.params.putAll(params);
        return this;
    }

    public OkHttpClientManager addParams(@NonNull String key,@NonNull String value){
        if (this.params == null){
            params = new LinkedHashMap<String, String>();
        }
        params.put(key,value);
        return this;
    }

    public OkHttpClientManager files(String key, Map<String, File> files)
    {
        if (this.files == null){
            this.files = new ArrayList<FileInput>();
        }
        for (String filename : files.keySet())
        {
            this.files.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    public OkHttpClientManager addFile(String name, String filename, File file)
    {
        if (this.files == null){
            this.files = new ArrayList<FileInput>();
        }
        files.add(new FileInput(name, filename, file));
        return this;
    }


    private void build(final OkHttpCallback... callbacks) {
        checkBuilder();
        checkUrl();
        switch (mMethod){
            case GET:
                mBuilder.url(appendParams(url,params)).get();
                break;
            case POST:
                //Form形式
                RequestBody formBody;
                if (files == null || files.isEmpty())
                {
                    FormBody.Builder builder = new FormBody.Builder();
                    addParams(builder);
                    formBody = builder.build();
                } else
                {
                    MultipartBody.Builder builder = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM);
                    addParams(builder);

                    for (int i = 0; i < files.size(); i++)
                    {
                        FileInput fileInput = files.get(i);
                        RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file);
                        builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
                    }
                    formBody = builder.build();
                }
                if (callbacks != null){
                    formBody = new CountingRequestBody(formBody, new CountingRequestBody.Listener() {
                        @Override
                        public void onRequestProgress(final long bytesWritten,final long contentLength) {
                            mPlatform.execute(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    for (int i = 0 ;i < callbacks.length;i++){
                                        callbacks[i].inProgress(bytesWritten * 1.0f / contentLength,contentLength,REQUEST_ID);
                                    }

                                }
                            });

                        }
                    });
                }
                mBuilder.url(url).post(formBody);
                //TODO raw形式
                /**
                * ({@link OkHttpClientManager#MEDIA_TYPE_PLAIN},{@link OkHttpClientManager#MEDIA_TYPE_STREAM})
                */

                break;
            case HEAD:
                mBuilder.url(appendParams(url,params)).head();
                break;
        }
    }

    public Response execute(){
        checkBuilder();
        checkUrl();
        build();
        Response response = null;
        Call requestCall = mOkHttpClient.newCall(mBuilder.build());
        try {
            response = requestCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public void enqueue(OkHttpCallback okHttpCallback){
        build(okHttpCallback);
        if (okHttpCallback == null){
            okHttpCallback = OkHttpCallback.okHttpCallbackDefault;
        }
        final OkHttpCallback finalOkHttpCallback = okHttpCallback;
        Call requestCall = mOkHttpClient.newCall(mBuilder.build());
        requestCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call,e, finalOkHttpCallback,REQUEST_ID);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try
                {
                    if (call.isCanceled())
                    {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalOkHttpCallback, REQUEST_ID);
                        return;
                    }

                    if (!finalOkHttpCallback.validateReponse(response, REQUEST_ID))
                    {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalOkHttpCallback, REQUEST_ID);
                        return;
                    }

                    Object o = finalOkHttpCallback.parseNetworkResponse(response, REQUEST_ID);
                    sendSuccessResultCallback(o, finalOkHttpCallback, REQUEST_ID);
                } catch (Exception e)
                {
                    sendFailResultCallback(call, e, finalOkHttpCallback, REQUEST_ID);
                } finally
                {
                    if (response.body() != null)
                        response.body().close();
                }
            }
        });
    }



    public static void setOkHttpClient(OkHttpClient okHttpClient){
        mOkHttpClient = okHttpClient;
    }


    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    public void sendFailResultCallback(final Call call, final Exception e, final OkHttpCallback okHttpCallback, final int id)
    {
        if (okHttpCallback == null) return;

        mPlatform.execute(new Runnable()
        {
            @Override
            public void run()
            {
                okHttpCallback.onError(call, e, id);
                okHttpCallback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final OkHttpCallback okHttpCallback, final int id)
    {
        if (okHttpCallback == null) return;
        mPlatform.execute(new Runnable()
        {
            @Override
            public void run()
            {
                okHttpCallback.onResponse(object, id);
                okHttpCallback.onAfter(id);
            }
        });
    }

    protected String appendParams(String url, Map<String, String> params)
    {
        if (url == null || params == null || params.isEmpty())
        {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    private void addParams(FormBody.Builder builder)
    {
        if (params != null)
        {
            for (String key : params.keySet())
            {
                builder.add(key, params.get(key));
            }
        }
    }

    private void addParams(MultipartBody.Builder builder)
    {
        if (params != null && !params.isEmpty())
        {
            for (String key : params.keySet())
            {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
    }

    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try
        {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void checkUrl() {
        if (url == null){
            throw new IllegalArgumentException("url can not be null.");
        }
    }

    private void checkBuilder() {
        if (mBuilder == null){
            throw new IllegalArgumentException("builder == null");
        }
    }

    public static void cancelByTag(Object tag) {
        if (mOkHttpClient == null){
            return;
        }
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


    public static void cancelAll() {
        if (mOkHttpClient == null){
            return;
        }
        mOkHttpClient.dispatcher().cancelAll();
    }

    public static class FileInput
    {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file)
        {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString()
        {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }

    public enum METHOD{
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE"),
        HEAD("HEAD"),
        PATCH("PATCH")
        ;

        private String value;
        METHOD(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }
}
