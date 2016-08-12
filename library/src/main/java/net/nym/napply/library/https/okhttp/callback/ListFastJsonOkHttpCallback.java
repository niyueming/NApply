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

package net.nym.napply.library.https.okhttp.callback;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import okhttp3.Response;

/**
 * @author niyueming
 * @date 2016-08-12
 * @time 09:43
 */
public abstract class ListFastJsonOkHttpCallback<T> extends OkHttpCallback<List<T>> {

    @Override
    public  List<T> parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        return JSON.parseArray(string,entityClass);
    }
}
