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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import net.nym.napply.library.entity.SimpleInfo;
import net.nym.napply.library.recycler.BaseRecyclerAdapter;
import net.nym.napply.library.recycler.RecyclerViewLinearDivider;
import net.nym.napply.library.recycler.SimpleAnimator;
import net.nym.napply.adapter.SimpleAdapter;
import net.nym.napply.library.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    SimpleAdapter mAdapter;
    List<SimpleInfo> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.setDebug(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewLinearDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setItemAnimator(new SimpleAnimator());
        mData = new ArrayList<SimpleInfo>();
        SimpleInfo info = new SimpleInfo();
        info.setName(HeadBottomScrollShowHideBehaviorActivity.class.getSimpleName());
        info.setClazz(HeadBottomScrollShowHideBehaviorActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(FollowAppBarBehaviorActivity.class.getSimpleName());
        info.setClazz(FollowAppBarBehaviorActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(HeadScrollSampleActivity.class.getSimpleName());
        info.setClazz(HeadScrollSampleActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(FrescoSampleActivity.class.getSimpleName());
        info.setClazz(FrescoSampleActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName(FrescoListSampleActivity.class.getSimpleName());
        info.setClazz(FrescoListSampleActivity.class);
        mData.add(info);
        info = new SimpleInfo();
        info.setName("");
        mData.add(info);

        mAdapter = new SimpleAdapter(this,mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
//                Snackbar.make(view, "position=" + position, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                SimpleInfo info = new SimpleInfo();
//                info.setName("add");
//                mData.add(position,info);
//                mAdapter.notifyItemInserted(position);

                startActivity(new Intent(MainActivity.this,mData.get(position).getClazz()));

            }
        });
        mAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position, long id) {
                mData.remove(position);
                mAdapter.notifyItemRemoved(position);
                return true;
            }
        });
    }

}
