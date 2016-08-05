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


import net.nym.napply.R;
import net.nym.napply.library.entity.SimpleInfo;
import net.nym.napply.library.recycler.BaseRecyclerAdapter;

import java.util.List;

/**
 * @author niyueming
 * @date 2016-08-05
 * @time 13:40
 */
public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.ViewHolder,SimpleInfo> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOT = 1;

    public SimpleAdapter(Context context, List<SimpleInfo> data) {
        super(context, data);
    }

    @Override
    protected void bindData(ViewHolder holder, SimpleInfo item) {
        holder.txt_name.setText(item.getName());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple_foot, parent, false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
    }

    protected class ViewHolder extends BaseRecyclerAdapter.ViewHolder {
        public TextView txt_name;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(View itemView) {
            txt_name = (TextView) itemView.findViewById(R.id.text);
        }

    }
}
