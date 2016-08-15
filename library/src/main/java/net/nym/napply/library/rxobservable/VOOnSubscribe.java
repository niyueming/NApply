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

package net.nym.napply.library.rxobservable;

import net.nym.napply.library.https.okhttp.callback.FastJsonGenericsSerializator;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;
import rx.Observable;

/**
 * @author niyueming
 * @date 2016-08-15
 * @time 11:09
 */
public abstract class VOOnSubscribe<T> extends FastJsonGenericsSerializator implements Observable.OnSubscribe<T> {


    public T parseNetworkResponse(String string) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        T bean = transform(string, entityClass);
        return bean;
    }
}
