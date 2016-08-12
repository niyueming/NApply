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

package net.nym.napply.library.recycler;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by niyueming on 16/8/3.
 */
public abstract class BaseRecyclerAdapter<VH extends BaseRecyclerAdapter.ViewHolder,T > extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<T> mData;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;


    public BaseRecyclerAdapter(Context context,List<T> data){
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (ViewHolder.class.isInstance(holder)){
            final VH viewHolder = (VH) holder;
            bindData(viewHolder,mData.get(position));
            if (mOnItemClickListener != null){
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(viewHolder.itemView,viewHolder.getAdapterPosition(),viewHolder.getItemId());
                    }
                });
            }

            if (mOnItemLongClickListener != null){
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return mOnItemLongClickListener.onItemLongClick(viewHolder.itemView,viewHolder.getAdapterPosition(),viewHolder.getItemId());
                    }
                } );
            }
        }
    }

    protected abstract void bindData(VH holder, T item);

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
//        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
//        notifyDataSetChanged();
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
            bindView(itemView);
        }

        protected abstract void bindView(View itemView);

        protected  <T extends View> void findViewById(View parent, @IdRes int id,T view){
            view = (T) parent.findViewById(id);
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position, long id);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(View view, int position, long id);
    }

}
