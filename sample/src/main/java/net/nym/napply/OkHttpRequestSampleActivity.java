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

package net.nym.napply;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import net.nym.napply.entity.Age;
import net.nym.napply.library.https.okhttp.OkHttpClientManager;
import net.nym.napply.library.https.okhttp.OkHttpRequest;
import net.nym.napply.library.https.okhttp.callback.ListFastJsonOkHttpCallback;
import net.nym.napply.library.https.okhttp.callback.StringOkHttpCallback;

import java.util.List;

import okhttp3.Call;

/**
 * @author niyueming
 * @date 2016-08-11
 * @time 18:02
 */
public class OkHttpRequestSampleActivity extends AppCompatActivity {
    private TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_request_sample_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text = (TextView) findViewById(R.id.text);
        new OkHttpRequest(this)
                .url("http://www.baidu.com")
                .tag(this)
                .method(OkHttpClientManager.METHOD.GET)
                .enqueue(new StringOkHttpCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        text.setText(response);
                    }
                });

    }

}
