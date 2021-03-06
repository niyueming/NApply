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

package net.nym.napply.library.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by niyueming on 16/6/1.
 */
public class SimpleInfo extends Entity implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        readObject(dest,this);
    }

    public static final Creator<SimpleInfo> CREATOR = new Creator<SimpleInfo>() {
        @Override
        public SimpleInfo createFromParcel(Parcel source) {
            SimpleInfo mMember = new SimpleInfo();
            writeObject(source, mMember);
            return mMember;
        }

        @Override
        public SimpleInfo[] newArray(int size) {
            return new SimpleInfo[size];
        }
    };

    private String id;
    private String name;
    private Class clazz;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}