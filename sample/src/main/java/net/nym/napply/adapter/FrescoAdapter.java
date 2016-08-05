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

package net.nym.napply.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import net.nym.napply.R;
import net.nym.napply.entity.ImageVo;
import net.nym.napply.library.recycler.BaseRecyclerAdapter;

import java.util.List;

/**
 * @author niyueming
 * @date 2016-08-05
 * @time 15:32
 */
public class FrescoAdapter extends BaseRecyclerAdapter<FrescoAdapter.ViewHolder,ImageVo> {


    public FrescoAdapter(Context context, List<ImageVo> data) {
        super(context, data);
    }

    @Override
    protected void bindData(ViewHolder holder, ImageVo item) {
        holder.image.setImageURI(item.getUrl());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fresco, parent, false);
        return new ViewHolder(view);
    }

    protected class ViewHolder extends BaseRecyclerAdapter.ViewHolder {

        SimpleDraweeView image;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(View itemView) {
            image = (SimpleDraweeView) itemView.findViewById(R.id.image);
        }

    }
}
