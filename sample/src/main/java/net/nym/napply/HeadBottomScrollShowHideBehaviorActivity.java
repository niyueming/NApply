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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.nym.napply.adapter.SimpleAdapter;
import net.nym.napply.library.entity.SimpleInfo;
import net.nym.napply.library.recycler.RecyclerViewLinearDivider;
import net.nym.napply.library.recycler.SimpleAnimator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by niyueming on 16/8/4.
 */
public class HeadBottomScrollShowHideBehaviorActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    SimpleAdapter mAdapter;
    List<SimpleInfo> mData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_bottom_scroll_show_hide_behavior_activity);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewLinearDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setItemAnimator(new SimpleAnimator());
        mData = new ArrayList<SimpleInfo>();
        for (int i = 0 ;i < 30;i++){
            SimpleInfo info = new SimpleInfo();
            info.setName("Test" + i);
            mData.add(info);
        }
        SimpleInfo info = new SimpleInfo();
        info.setName("");
        mData.add(info);

        mAdapter = new SimpleAdapter(this,mData);
        mRecyclerView.setAdapter(mAdapter);
    }
}
