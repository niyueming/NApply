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

import net.nym.napply.library.cookie.CookieJarImpl;
import net.nym.napply.library.cookie.store.PersistentCookieStore;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author niyueming
 * @date 2016-08-10
 * @time 14:24
 */
public class OkHttpClientManager {
    private static OkHttpClientManager instance;
    private volatile static OkHttpClient mOkHttpClient;
    private final static long MAX_CACHE_SIZE = 50 * 1024 * 1024 * 8;
    private final static int CONNECT_TIMEOUT = 60;  //秒
    private final static int READ_TIMEOUT = 60;  //秒
    private final static int WRITE_TIMEOUT = 60;  //秒

    private OkHttpClientManager(Context context) {
        if (mOkHttpClient == null){
            initHttpClient(context);
        }
    }

    public static OkHttpClientManager getInstance(Context context) {
        if (instance == null) {
            synchronized (OkHttpClientManager.class) {
                if (instance == null) {
                    instance = new OkHttpClientManager(context);
                }
            }
        }
        return instance;
    }

    private void initHttpClient(Context context){
        //        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null,null,null);
        mOkHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new LoggerInterceptor("NApply", true))
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
                .build();
    }


    public void fun() {
//        Request request = new Request.Builder().build().;
//        mOkHttpClient.newCall().;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient){
        mOkHttpClient = okHttpClient;
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    public void cancelByTag(Object tag) {
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
}
