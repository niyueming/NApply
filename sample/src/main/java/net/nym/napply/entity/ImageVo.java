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

package net.nym.napply.entity;

import android.os.Parcel;
import android.os.Parcelable;

import net.nym.napply.library.entity.Entity;

/**
 * @author niyueming
 * @date 2016-08-05
 * @time 15:38
 */
public class ImageVo extends Entity implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        readObject(dest,this);
    }

    public static final Creator<ImageVo> CREATOR = new Creator<ImageVo>() {
        @Override
        public ImageVo createFromParcel(Parcel source) {
            ImageVo mMember = new ImageVo();
            writeObject(source, mMember);
            return mMember;
        }

        @Override
        public ImageVo[] newArray(int size) {
            return new ImageVo[size];
        }
    };

    private String id;
    private String name;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}