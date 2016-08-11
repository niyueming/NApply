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
import android.support.annotation.NonNull;

import net.nym.napply.library.https.IRequest;
import net.nym.napply.library.https.okhttp.callback.OkHttpCallback;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;

/**
 * @author niyueming
 * @date 2016-08-10
 * @time 14:21
 */
public class OkHttpRequest implements IRequest<OkHttpCallback>{
    private Call mCall;

    private OkHttpClientManager mManager;

    public OkHttpRequest(Context context){
        mManager = OkHttpClientManager.getInstance(context);
        mManager.newBuilder();
    }

    public OkHttpRequest method(OkHttpClientManager.METHOD method){
        mManager.method(method);
        return this;
    }

    public OkHttpRequest addHeader(@NonNull String name, @NonNull String value){
        mManager.addHeader(name,value);
        return this;
    }

    public OkHttpRequest addHeader(@NonNull Map<String, String> headers){
        mManager.addHeader(Headers.of(headers));
        return this;
    }

    public OkHttpRequest url(@NonNull String url){
        mManager.url(url);
        return this;
    }

    public OkHttpRequest tag(@NonNull Object tag){
        mManager.tag(tag);
        return this;
    }

    public OkHttpRequest params(@NonNull Map<String, String> params){
        mManager.params(params);
        return this;
    }

    public OkHttpRequest addParams(@NonNull String key,@NonNull String value){
        mManager.addParams(key,value);
        return this;
    }

    public OkHttpRequest files(String key, Map<String, File> files)
    {
        mManager.files(key,files);
        return this;
    }

    public OkHttpRequest addFile(String name, String filename, File file)
    {
       mManager.addFile(name,filename,file);
        return this;
    }

    @Override
    public boolean cancel() {
        if (mCall != null){
            mCall.cancel();
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        mCall = mManager.execute();
    }

    @Override
    public void enqueue(OkHttpCallback okHttpCallback) {
        mCall = mManager.enqueue(okHttpCallback);
    }
}
